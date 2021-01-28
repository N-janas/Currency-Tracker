package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.view.View
import android.widget.AdapterView

class SpinnerOnClickListener(private val ICurrencyConverter: ICurrencyConverter) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        ICurrencyConverter.setIsConverted(false)
        ICurrencyConverter.changeButtonIcon()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        ICurrencyConverter.setIsConverted(false)
        ICurrencyConverter.changeButtonIcon()
    }

}