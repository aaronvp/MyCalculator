package aaronp.udemy.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        // Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false


    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let{
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue?.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    var result = one.toDouble() - two.toDouble()

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot(result)
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    var result = one.toDouble() + two.toDouble()

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot(result)
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    var result = one.toDouble() / two.toDouble()

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot(result)
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    var result = one.toDouble() * two.toDouble()

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot(result)
                }

            } catch (e: ArithmeticException) {

            }
        }
    }

    private fun removeZeroAfterDot(result: Double) : String {
        var value = result.toString()
        if (value.endsWith(".0")) {
            value = value.substring(0, value.length - 2)
        }
        return value;
    }

}