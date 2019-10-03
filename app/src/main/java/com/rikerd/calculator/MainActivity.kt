package com.rikerd.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

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
        }

        btnBack.setOnClickListener {
            val expressionString = expression.text.toString()

            if (expressionString.isNotEmpty()) {
                expression.text = expressionString.substring(0, expressionString.length - 1)
            }

            result.text = ""
        }
    }

    fun appendOnExpression(string: String, canClear: Boolean) {
        if (canClear) {
            result.text = ""
            expression.append(string)
        }
        else {
            expression.append(result.text)
            expression.append(string)
            result.text = ""
        }
    }
}
