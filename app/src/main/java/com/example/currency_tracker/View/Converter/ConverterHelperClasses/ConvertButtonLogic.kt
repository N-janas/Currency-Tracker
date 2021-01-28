package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.airbnb.lottie.ImageAssetDelegate
import com.airbnb.lottie.LottieAnimationView
import com.example.currency_tracker.R
import com.example.currency_tracker.ViewModel.ConverterViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class ConvertButtonLogic(private val view: View, private val converterViewModel: ConverterViewModel): ICurrencyConverter {
    private var isConverted:Boolean = false
    private val baseEditText: EditText = view.new_converter_baseEditText
    private val symbolEditText: EditText = view.new_converter_symbolEditText
    private val symbolSpinner: Spinner = view.new_converter_symbolSpinner
    private val baseSpinner: Spinner = view.new_converter_baseSpinner
    private val convertButton: LottieAnimationView = view.new_converter_convertButton

    // Convert or swap currencies
    fun convertOrSwapCurrencies() {
        if (!isConverted) {
            if (isValidInput()) {
                convertAction()
                setIsConverted(true)
                changeButtonIcon()
            } else {
                setErrorOnInvalidInput()
            }

        } else {
            swapCurrencies()
            setIsConverted(false)
            changeButtonIcon()
        }
    }

    private fun setErrorOnInvalidInput() {
        baseEditText.error = "Podaj wartość"
    }

    private fun convertAction() {
        converterViewModel.getLatestRatesBetweenTwo(
                symbolSpinner.selectedItem.toString(),
                baseSpinner.selectedItem.toString(),
                baseEditText.text.toString().toDouble()
        ).toString()

    }

    private fun isValidInput(): Boolean {
        return !(baseEditText.text.isEmpty() || baseEditText.text.toString().toDouble() == 0.0)
    }

    override fun setIsConverted(converted: Boolean) {
        isConverted = converted
    }

    override fun changeButtonIcon() {
        if (isConverted) {
            convertButton.pauseAnimation()
            convertButton.playAnimation()
        } else {
            val res = view.context.resources
            val id = R.drawable.ic_baseline_arrow_forward_24
            val bitmapImage = BitmapFactory.decodeResource(res,id)
            convertButton.setImageAssetDelegate { bitmapImage }
            //convertButton.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
        }
    }

    private fun swapCurrencies() {

        val baseItemPosition = baseSpinner.selectedItemPosition
        val symbolItemPosition = symbolSpinner.selectedItemPosition

        baseSpinner.setSelection(symbolItemPosition)
        symbolSpinner.setSelection(baseItemPosition)

        val baseValue = baseEditText.text
        val symbolValue = symbolEditText.text

        symbolEditText.setText(baseValue.toString())
        baseEditText.setText(symbolValue.toString())

    }
}