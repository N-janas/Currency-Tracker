package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.view.View
import android.widget.AdapterView

class SpinnerOnClickListener(private val currencyConverter: ICurrencyConverter, private val currencyFavourite: ICurrencyFavourite) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currencyConverter.setIsConverted(false)
        currencyConverter.changeButtonIcon()
        currencyFavourite.refreshView()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        currencyConverter.setIsConverted(false)
        currencyConverter.changeButtonIcon()
        currencyFavourite.refreshView()
    }

}