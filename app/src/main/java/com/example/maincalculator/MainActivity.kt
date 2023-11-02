package com.example.maincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var dataTv: TextView
    private lateinit var resultTv: TextView
    private var lastNumeric = false
    private var stateError = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataTv = findViewById(R.id.data_tv)
        resultTv = findViewById(R.id.result_Tv)
    }

    fun onClearAllClick(view: View) {
        dataTv.text = ""
        resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
    }

    fun onClearClick(view: View) {
        dataTv.text = ""
        lastNumeric = false
    }

    fun onBackClick(view: View) {
        val currentText = dataTv.text.toString()
        if (currentText.isNotEmpty()) {
            dataTv.text = currentText.dropLast(1)
        }
    }

    fun onOperatorClick(view: View) {
        if (lastNumeric && !stateError) {
            dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
        }
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            dataTv.text = (view as Button).text
            stateError = false
        } else {
            dataTv.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onEqualClick(view: View) {
        onEqual()
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val expression = ExpressionBuilder(dataTv.text.toString()).build()
            try {
                val result = expression.evaluate()
                resultTv.visibility = View.VISIBLE
                resultTv.text = "= $result"
            } catch (ex: ArithmeticException) {
                resultTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}
