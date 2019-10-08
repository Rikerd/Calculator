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
        btnZero.setOnClickListener { appendOnExpression("0", true) }
        btnOne.setOnClickListener { appendOnExpression("1", true) }
        btnTwo.setOnClickListener { appendOnExpression("2", true) }
        btnThree.setOnClickListener { appendOnExpression("3", true) }
        btnFour.setOnClickListener { appendOnExpression("4", true) }
        btnFive.setOnClickListener { appendOnExpression("5", true) }
        btnSix.setOnClickListener { appendOnExpression("6", true) }
        btnSeven.setOnClickListener { appendOnExpression("7", true) }
        btnEight.setOnClickListener { appendOnExpression("8", true) }
        btnNine.setOnClickListener { appendOnExpression("9", true) }
        btnDot.setOnClickListener { appendOnExpression(".", true) }

        // Operators click listener
        btnPlus.setOnClickListener { appendOnExpression("+", false) }
        btnMinus.setOnClickListener { appendOnExpression("-", false) }
        btnMult.setOnClickListener { appendOnExpression("*", false) }
        btnDiv.setOnClickListener { appendOnExpression("/", false) }
        btnOpenPar.setOnClickListener { appendOnExpression("(", false) }
        btnClosePar.setOnClickListener { appendOnExpression(")", false) }

        btnCE.setOnClickListener {
            expression.text = ""
            result.text = ""
            vibratePhone()
        }

        btnBack.setOnClickListener {
            val expressionString = expression.text.toString()

            if (expressionString.isNotEmpty()) {
                expression.text = expressionString.substring(0, expressionString.length - 1)
            }

            result.text = ""
            vibratePhone()
        }

        btnEq.setOnClickListener {
            try {
                val expression = ExpressionBuilder(expression.text.toString()).build()
                val calcResult = expression.evaluate()
                val longCalcResult = calcResult.toLong()

                if (calcResult == longCalcResult.toDouble()) {
                    result.text = longCalcResult.toString()
                }
                else {
                    result.text = calcResult.toString()
                }
            } catch(e: Exception) {
                result.text = "ERROR"
                Log.d("Exception", " message : " + e.message)
            }
            vibratePhone()
        }
    }

    fun appendOnExpression(string: String, canClear: Boolean) {
        if (result.text.isNotEmpty()) {
            expression.text = ""
        }

        if (canClear) {
            result.text = ""
            expression.append(string)
        }
        else {
            expression.append(result.text)
            expression.append(string)
            result.text = ""
        }

        vibratePhone()
    }

    fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        Log.d(Build.VERSION.SDK_INT.toString(), "hi")

        if (Build.VERSION.SDK_INT >= 29) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.EFFECT_CLICK))
        }
        else
        {
            vibrator.vibrate(VibrationEffect.createOneShot(20, 100))
        }
    }
}
