package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class ConverterListeners(view: View, private val convertButtonLogic: ConvertButtonLogic){
    private var baseSpinner: Spinner = view.new_converter_baseSpinner
    private var symbolSpinner : Spinner = view.new_converter_symbolSpinner
    private var baseEditText: EditText = view.new_converter_baseEditText
    private var convertButton: FloatingActionButton = view.new_converter_convertButton
    private var chartButton: FloatingActionButton = view.new_converter_chartButton
    private var favoriteButton: FloatingActionButton = view.new_converter_favoriteButton
    private var context: Context = view.context


    fun initializeAllListeners(){
        baseSpinner.onItemSelectedListener = SpinnerOnClickListener(convertButtonLogic)
        symbolSpinner.onItemSelectedListener = SpinnerOnClickListener(convertButtonLogic)
        baseEditText.addTextChangedListener(Watcher(convertButtonLogic))


        convertButton.setOnClickListener {
            convertButtonLogic.convertOrSwapCurrencies()
        }

        chartButton.setOnClickListener {

        }

        favoriteButton.setOnClickListener {
            addToFavoriteCurrentSet()
        }
    }

    private fun addToFavoriteCurrentSet() {
        if (setNotExist()) {

        } else {
            Toast.makeText(context, "Już istnieje w ulubionych", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNotExist(): Boolean {
        return false
    }

}