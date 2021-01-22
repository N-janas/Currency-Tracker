package com.example.currency_tracker.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currency_tracker.R
import com.example.currency_tracker.ViewModel.ConverterViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.new_fragment_converter.*
import kotlinx.android.synthetic.main.new_fragment_converter.view.*


class ConverterFragment : Fragment(),CurrencyConverterView {
    private var isConverted: Boolean = false
    private lateinit var converterViewModel: ConverterViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        converterViewModel = ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer for changes in result
        val resultObserver = Observer<String>{ result -> view.new_converter_symbolEditText.setText(result) }

        // Connect result's observer
        converterViewModel.conversionResult.observe(viewLifecycleOwner, resultObserver)

        val convertButton =  new_converter_convertButton as FloatingActionButton
        val chartButton = new_converter_chartButton as FloatingActionButton
        val favoriteButton = new_converter_favoriteButton as FloatingActionButton
        val baseSpinner = new_converter_baseSpinner as Spinner
        val symbolSpinner = new_converter_symbolSpinner as Spinner
        val baseEditText = new_converter_baseEditText as EditText

        convertButton.setOnClickListener {
            convertOrSwapCurrencies()
        }

        baseSpinner.onItemSelectedListener = CurrencySpinnerAdapter(this)
        symbolSpinner.onItemSelectedListener = CurrencySpinnerAdapter(this)
        baseEditText.addTextChangedListener(Watcher(this))
    }

    // Convert or swap currencies
    private fun convertOrSwapCurrencies() {
        if(!isConverted){
            if(isValidInput()){
                convertAction()
                setState(true)
                changeButton()
            }
            else{
                //TODO DING DONG WRONG
            }

        }
        else{
            swapCurrencies()
            setState(false)
            changeButton()
        }
    }

    private fun convertAction() {
            converterViewModel.getLatestRatesBetweenTwo(
                    new_converter_symbolSpinner.selectedItem.toString(),
                    new_converter_baseSpinner.selectedItem.toString(),
                    new_converter_baseEditText.text.toString().toDouble()
            ).toString()

    }

    private fun isValidInput(): Boolean {
        return !(new_converter_baseEditText.text.isEmpty()|| new_converter_baseEditText.text.toString().toDouble() == 0.0)

    }

    override fun setState(converted: Boolean) {
        isConverted = converted
    }

    override fun changeButton() {
        if(isConverted){
            new_converter_convertButton.setImageResource(R.drawable.ic_baseline_repeat_24)
        }
        else{
            new_converter_convertButton.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
        }
    }

    private fun swapCurrencies() {
        val base = new_converter_baseSpinner
        val symbol = new_converter_symbolSpinner

        val baseItemPosition = base.selectedItemPosition
        val symbolItemPosition = symbol.selectedItemPosition

        base.setSelection(symbolItemPosition)
        symbol.setSelection(baseItemPosition)

        val baseValue = new_converter_baseEditText?.text
        val symbolValue = new_converter_symbolEditText?.text

        new_converter_symbolEditText.setText(baseValue.toString())
        new_converter_baseEditText.setText(symbolValue.toString())

    }
}


