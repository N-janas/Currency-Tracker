package com.example.currency_tracker.View

import android.view.View
import android.widget.AdapterView

class CurrencySpinnerAdapter(private val currencyConverterView: CurrencyConverterView) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currencyConverterView.setState(false)
        currencyConverterView.changeButton()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        currencyConverterView.setState(false)
        currencyConverterView.changeButton()
    }

}