package com.example.currency_tracker.View

import android.text.Editable
import android.text.TextWatcher

class Watcher(private val currencyConverterView: CurrencyConverterView) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        currencyConverterView.setState(false)
        currencyConverterView.changeButton()
    }
}