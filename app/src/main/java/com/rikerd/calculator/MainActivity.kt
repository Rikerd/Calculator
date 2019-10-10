package com.rikerd.calculator

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Numbers click listener
        btnZero.setOnClickListener { appendOnExpression("0", 0) }
        btnOne.setOnClickListener { appendOnExpression("1", 0) }
        btnTwo.setOnClickListener { appendOnExpression("2", 0) }
        btnThree.setOnClickListener { appendOnExpression("3", 0) }
        btnFour.setOnClickListener { appendOnExpression("4", 0) }
        btnFive.setOnClickListener { appendOnExpression("5", 0) }
        btnSix.setOnClickListener { appendOnExpression("6", 0) }
        btnSeven.setOnClickListener { appendOnExpression("7", 0) }
        btnEight.setOnClickListener { appendOnExpression("8", 0) }
        btnNine.setOnClickListener { appendOnExpression("9", 0) }
        btnDot.setOnClickListener { appendOnExpression(".", 0) }

        // Operators click listener
        btnPlus.setOnClickListener { appendOnExpression("+", 2) }
        btnMinus.setOnClickListener { appendOnExpression("-", 2) }
        btnMult.setOnClickListener { appendOnExpression("*", 2) }
        btnDiv.setOnClickListener { appendOnExpression("/", 2) }
        btnOpenPar.setOnClickListener { appendOnExpression("(", 1) }
        btnClosePar.setOnClickListener { appendOnExpression(")", 2) }

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

    fun appendOnExpression(string: String, typeOfAppend: Int) {
        // typeOfAppend determines how it should be appended
        // 0: Clear result and append
        // 1: Able to append at start of empty expression or after result
        // 2: Only able to append if expression is not empty

        if (typeOfAppend == 0) {
            if (result.text.isNotEmpty()) {
                expression.text = ""
            }

            result.text = ""
            expression.append(string)
        }
        else if (typeOfAppend == 1)
        {
            if (result.text.isNotEmpty()) {
                expression.text = ""
            }

            if (result.text != "ERROR") {
                expression.append(result.text)
            }
            expression.append(string)
            result.text = ""
        }
        else if (typeOfAppend == 2)
        {
            if (expression.text.isNotEmpty()) {
                if (result.text.isNotEmpty()) {
                    expression.text = ""
                }

                if (result.text != "ERROR") {
                    expression.append(result.text)
                }
                expression.append(string)
                result.text = ""
            }
        }

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
