package com.example.currency_tracker.View.Converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.currency_tracker.R
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.ConvertButtonLogic
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.ConverterListeners
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.MyDropDownMenu
import com.example.currency_tracker.ViewModel.ConverterViewModel
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class ConverterFragment : Fragment() {
    private val args by navArgs<ConverterFragmentArgs>()

    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var myDropDownMenu: MyDropDownMenu
    private lateinit var converterListeners: ConverterListeners
    private lateinit var convertButtonLogic: ConvertButtonLogic

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_fragment_converter, container, false)
        converterViewModel = ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)
        myDropDownMenu = MyDropDownMenu(view)
        convertButtonLogic = ConvertButtonLogic(view,converterViewModel)
        converterListeners = ConverterListeners(view,convertButtonLogic/*tu będą kolejne logiki dla przycisków*/)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultObserver = Observer<String> { result -> view.new_converter_symbolEditText.setText(result) }
        converterViewModel.conversionResult.observe(viewLifecycleOwner, resultObserver)
        if(argumentsAreNotNull()){
            myDropDownMenu.initializeSpinnersWithArguments(args)
        }
        converterListeners.initializeAllListeners()
        view.new_converter_baseEditText.requestFocus()
    }

    private fun argumentsAreNotNull(): Boolean {
        if (args.base.isEmpty() || args.symbol.isEmpty()) {
            return false
        }
        return true
    }

}


