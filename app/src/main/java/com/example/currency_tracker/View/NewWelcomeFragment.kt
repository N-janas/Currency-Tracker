package com.example.currency_tracker.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_tracker.R
import com.example.currency_tracker.ViewModel.FavouritesViewModel
import kotlinx.android.synthetic.main.new_fragment_welcome.view.*


class NewWelcomeFragment : Fragment() {

    private lateinit var mFavouritesViewModel: FavouritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.new_fragment_welcome, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.new_welcome_recyclerView)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        val flags = resources.obtainTypedArray(R.array.countries_flags)
        val currencies = resources.getStringArray(R.array.supported_currencies)
        val adapter = FavouritesListAdapter(currencies,flags,findNavController())
        recyclerView.adapter = adapter
        mFavouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        mFavouritesViewModel.readAllData.observe(viewLifecycleOwner, Observer { fav->
            adapter.setData(fav)
        })

        view.new_welcome_addButton.setOnClickListener {
            val action = NewWelcomeFragmentDirections.actionNewWelcomeFragmentToNewConverterFragment("","")
            findNavController().navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

