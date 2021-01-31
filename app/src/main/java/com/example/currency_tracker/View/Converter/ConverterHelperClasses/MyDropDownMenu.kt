package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.currency_tracker.R
import com.example.currency_tracker.View.Converter.ConverterFragmentArgs
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class MyDropDownMenu(view: View){
    private var baseSpinner: Spinner = view.new_converter_baseSpinner
    private var symbolSpinner: Spinner = view.new_converter_symbolSpinner
    private var context: Context = view.context

    fun initializeSpinnersWithArguments(args: ConverterFragmentArgs) {
        val spinnerAdapter = initializeAdapterForSpinner()
        baseSpinner.adapter = spinnerAdapter
        symbolSpinner.adapter = spinnerAdapter

        var position = spinnerAdapter.getPosition(args.base)
        setBaseSpinner(position)
        position = spinnerAdapter.getPosition(args.symbol)
        setSymbolSpinner(position)
    }

    fun initializeSpinnersWithDefault() {
        setBaseSpinner(0)
        setSymbolSpinner(0)
    }

    private fun initializeAdapterForSpinner(): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(
                context,
                R.array.supported_currencies,
                android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }


    private fun setSymbolSpinner(position: Int) {
        symbolSpinner.setSelection(position)
    }

    private fun setBaseSpinner(position:Int) {
        baseSpinner.setSelection(position)
    }

}