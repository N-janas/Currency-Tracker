package com.example.currency_tracker.View.Converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.R
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.ConvertButtonLogic
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.ConverterOnClickListeners
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.FavouriteButtonLogic
import com.example.currency_tracker.View.Converter.ConverterHelperClasses.MyDropDownMenu
import com.example.currency_tracker.ViewModel.ConverterViewModel
import com.example.currency_tracker.ViewModel.FavouritesViewModel
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class ConverterFragment : Fragment() {
    private val args by navArgs<ConverterFragmentArgs>()

    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var myDropDownMenu: MyDropDownMenu
    private lateinit var converterOnClickListeners: ConverterOnClickListeners
    private lateinit var convertButtonLogic: ConvertButtonLogic
    private lateinit var favouriteButtonLogic: FavouriteButtonLogic
    private lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_fragment_converter, container, false)
        converterViewModel = ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)
        favouritesViewModel = ViewModelProvider(requireActivity()).get(FavouritesViewModel::class.java)
        myDropDownMenu = MyDropDownMenu(view)
        convertButtonLogic = ConvertButtonLogic(view,converterViewModel)
        favouriteButtonLogic = FavouriteButtonLogic(view,requireActivity())
        converterOnClickListeners = ConverterOnClickListeners(view,convertButtonLogic,favouriteButtonLogic)
        //Magic starts here
        val obs = Observer<List<Favourites>> {arg-> Log.d("Fav",arg.joinToString())  }
        favouritesViewModel.readAllData.observe(viewLifecycleOwner,obs)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultObserver = Observer<String> { result -> view.new_converter_symbolEditText.setText(result) }
        converterViewModel.conversionResult.observe(viewLifecycleOwner, resultObserver)
        if(argumentsAreNotNull()){
            myDropDownMenu.initializeSpinnersWithArguments(args)
        }
        converterOnClickListeners.initializeAllListeners()

        view.new_converter_baseEditText.requestFocus()
    }

    private fun argumentsAreNotNull(): Boolean {
        if (args.base.isEmpty() || args.symbol.isEmpty()) {
            return false
        }
        return true
    }

}


