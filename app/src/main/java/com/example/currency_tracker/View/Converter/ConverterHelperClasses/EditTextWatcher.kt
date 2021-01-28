package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.text.Editable
import android.text.TextWatcher

class EditTextWatcher(private val ICurrencyConverter: ICurrencyConverter) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        ICurrencyConverter.setIsConverted(false)
        ICurrencyConverter.changeButtonIcon()
    }
}