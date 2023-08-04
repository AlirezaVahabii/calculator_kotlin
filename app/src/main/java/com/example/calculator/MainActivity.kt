package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var budding: ActivityMainBinding
    var isLastInputNumber = false
    var isDotInputText = false
    var nextActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        budding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(budding.root)
        budding.textView.showSoftInputOnFocus = false
        budding.textViewAnwser.showSoftInputOnFocus = false
    }

    fun delet(view: View) {
        if (budding.textView.text.endsWith("."))
            isDotInputText = false
        budding.textView.setText(budding.textView.text.dropLast(1))
    }


    fun onNumClick(view: View) {
        budding.textView.append((view as Button).text)
        isLastInputNumber = true
    }

    fun buttonDot(view: View) {
        if (isLastInputNumber && !isDotInputText) {
            budding.textView.append(".")
            isDotInputText = true
        }
    }

    fun buttonClear(view: View) {
        budding.textView.setText("")
        budding.textViewAnwser.setText("")
        nextActive = false
        isLastInputNumber = false
        isDotInputText = false
    }

    fun onOperatorClicker(view: View) {
        if(nextActive){
            budding.textView.setText(budding.textViewAnwser.text)
            budding.textViewAnwser.setText("")
        }
        if (isLastInputNumber && !isOperatorSelec(budding.textView.text.toString())) {
            budding.textView.append((view as Button).text)
            isLastInputNumber = false
            isDotInputText = false
        }
    }

    fun isOperatorSelec(text: String): Boolean {
        if (text.startsWith("-"))
            return false
        if (text.contains("/") || text.contains("*") || text.contains("-") || text.contains("+"))
            return true
        return false
    }

    fun onEqualClick(view: View) {
        nextActive = true
        var prefix = ""
        if (isLastInputNumber) {
            var inputValue = budding.textView.text.toString()
            if (inputValue.contains("+")) {
                val splitValueArray = inputValue.split("+")
                budding.textViewAnwser.setText((splitValueArray[0].toDouble() + splitValueArray[1].toDouble()).toString())
            }
            if (inputValue.contains("*")) {
                val splitValueArray = inputValue.split("*")
                budding.textViewAnwser.setText((splitValueArray[0].toDouble() * splitValueArray[1].toDouble()).toString())
            }
            if (inputValue.contains("/")) {
                val splitValueArray = inputValue.split("/")
                if (!prefix.isEmpty())
                    prefix + splitValueArray[0]
                budding.textViewAnwser.setText((splitValueArray[0].toDouble() / splitValueArray[1].toDouble()).toString())
            }
            if (inputValue.startsWith("-")) {
                prefix = "-"
                inputValue = inputValue.substring(1)
            }
            if (inputValue.contains("-")) {
                var splitValueArray = inputValue.split("-")
                if (!prefix.isEmpty())
                    prefix + splitValueArray[1]
                budding.textViewAnwser.setText((splitValueArray[0].toDouble() - splitValueArray[1].toDouble()).toString())
            }
        }
    }
}