package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class ConverterOnClickListeners(view: View,
                                private val convertButtonLogic: ConvertButtonLogic,
                                private val favouriteButtonLogic: FavouriteButtonLogic){
    private var baseSpinner: Spinner = view.new_converter_baseSpinner
    private var symbolSpinner : Spinner = view.new_converter_symbolSpinner
    private var baseEditText: EditText = view.new_converter_baseEditText
    private var convertButton: LottieAnimationView = view.new_converter_convertButton
    private var chartButton: FloatingActionButton = view.new_converter_chartButton
    private var favoriteButton: LottieAnimationView = view.new_converter_favoriteButton
    private var context: Context = view.context


    fun initializeAllListeners(){
        baseSpinner.onItemSelectedListener = SpinnerOnClickListener(convertButtonLogic,favouriteButtonLogic)
        symbolSpinner.onItemSelectedListener = SpinnerOnClickListener(convertButtonLogic,favouriteButtonLogic)
        baseEditText.addTextChangedListener(EditTextWatcher(convertButtonLogic))


        convertButton.setOnClickListener {
            convertButtonLogic.convertOrSwapCurrencies()
        }

        chartButton.setOnClickListener {

        }

        favoriteButton.setOnClickListener {
            favouriteButtonLogic.addToFavourites()
        }
    }

}