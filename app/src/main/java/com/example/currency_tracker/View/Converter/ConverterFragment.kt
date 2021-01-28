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
    // ustawia rozwijane listy w widoku
    private lateinit var myDropDownMenu: MyDropDownMenu
    // ustawia które elementy reagują na kliknięcia
    private lateinit var converterListeners: ConverterListeners
    // logika dla przycisku, przekazywana do ConverterListeners
    private lateinit var convertButtonLogic: ConvertButtonLogic
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.new_fragment_converter, container, false)
        converterViewModel = ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)
        myDropDownMenu = MyDropDownMenu(view)
        convertButtonLogic = ConvertButtonLogic(view,converterViewModel)
        converterListeners = ConverterListeners(view,convertButtonLogic/*tu będą kolejne logiki dla przycisków*/)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observer for changes in result
        val resultObserver = Observer<String> { result -> view.new_converter_symbolEditText.setText(result) }
        // Connect result's observer
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


