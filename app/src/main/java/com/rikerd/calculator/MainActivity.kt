package com.rikerd.calculator

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Numbers click listener
        btnZero.setOnClickListener { appendDigit("0") }
        btnOne.setOnClickListener { appendDigit("1") }
        btnTwo.setOnClickListener { appendDigit("2") }
        btnThree.setOnClickListener { appendDigit("3") }
        btnFour.setOnClickListener { appendDigit("4") }
        btnFive.setOnClickListener { appendDigit("5") }
        btnSix.setOnClickListener { appendDigit("6") }
        btnSeven.setOnClickListener { appendDigit("7") }
        btnEight.setOnClickListener { appendDigit("8") }
        btnNine.setOnClickListener { appendDigit("9") }
        btnDot.setOnClickListener { appendDigit(".") }

        // Operators click listener
        btnPlus.setOnClickListener { appendOperator("+") }
        btnMinus.setOnClickListener { appendOperator("-") }
        btnMult.setOnClickListener { appendOperator("*") }
        btnDiv.setOnClickListener { appendOperator("/") }
        btnOpenPar.setOnClickListener { appendOpenPar("(") }
        btnClosePar.setOnClickListener { appendOperator(")") }

        btnTip.setOnClickListener { appendOperator("*1.15") }
        btnTax.setOnClickListener { appendOperator("*1.0725") }

        btnSign.setOnClickListener { modifySign() }

        btnCE.setOnClickListener {
            expression.text = ""
            result.text = ""
            vibratePhone()
        }

        btnBack.setOnClickListener {
            val expressionString = expression.text.toString()

            if (expressionString.isNotEmpty())
            {
                expression.text = expressionString.substring(0, expressionString.length - 1)
            }

            result.text = ""
            vibratePhone()
        }

        btnEq.setOnClickListener {
            try {
                val expressionResult = ExpressionBuilder(expression.text.toString()).build()
                val calcResult = expressionResult.evaluate()
                val longCalcResult = calcResult.toLong()

                if (calcResult == longCalcResult.toDouble()) {
                    result.text = longCalcResult.toString()
                }
                else
                {
                    result.text = calcResult.toString()
                }
            }
            catch(e: Exception)
            {
                result.text = "ERROR"
                Log.d("Exception", " message : " + e.message)
            }
            vibratePhone()
        }
    }

    // Modifies the sign of number to be positive or negative
    fun modifySign() {
        if (expression.text.isNotEmpty()) {
            if (result.text.isNotEmpty()) {
                expression.text = ""
            }

            if (result.text != "ERROR") {
                if (result.text == "")
                {
                    val endDigits = expression.text.takeLastWhile { it.isDigit() }

                    if (endDigits.isEmpty()) {
                        result.text = ""
                        vibratePhone()
                        return
                    }

                    expression.text = expression.text.trimEnd{ it.isDigit() }

                    if (expression.text.endsWith("(-")) {
                        expression.text = expression.text.dropLast(2)
                        expression.append(endDigits)
                    }
                    else
                    {
                        expression.append("(-" + endDigits)

                    }
                }
                else
                {
                    expression.append(result.text)

                    if (expression.text.startsWith('-')) {
                        expression.text = expression.text.drop(1)
                    }
                    else
                    {
                        expression.text = "-" + expression.text
                    }
                }
            }
            result.text = ""
        }

        vibratePhone()
    }

    // Appends the proper operator the end after checking conditions
    fun appendOperator(string: String) {
        if (expression.text.isNotEmpty()) {
            if (result.text.isNotEmpty()) {
                expression.text = ""
            }

            if (result.text != "ERROR") {
                if (result.text == "")
                {
                    if (expression.text.endsWith('*') || expression.text.endsWith('+') ||
                        expression.text.endsWith('/') || expression.text.endsWith('-')) {
                        expression.text = expression.text.dropLast(1)
                        expression.append(string)
                    }
                    else
                    {
                        expression.append(string)
                    }
                }
                else
                {
                    expression.append(result.text)
                    expression.append(string)
                }
            }
            result.text = ""
        }

        vibratePhone()
    }

    fun appendOpenPar(string: String) {
        if (result.text.isNotEmpty()) {
            expression.text = ""
        }

        if (result.text != "ERROR") {
            expression.append(result.text)
        }
        expression.append(string)
        result.text = ""

        vibratePhone()
    }

    fun appendDigit(string: String) {
        if (result.text.isNotEmpty()) {
            expression.text = ""
        }

        result.text = ""
        expression.append(string)

        vibratePhone()
    }

    fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= 29) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.EFFECT_CLICK))
        }
        else
        {
            vibrator.vibrate(VibrationEffect.createOneShot(20, 100))
        }
    }
}
