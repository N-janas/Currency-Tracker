package com.example.currency_tracker.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.currency_tracker.R
import com.example.currency_tracker.ViewModel.ConverterViewModel
import kotlinx.android.synthetic.main.fragment_converter.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConverterFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var converterViewModel: ConverterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Convert currencies
        btnConvert.setOnClickListener {
            converterViewModel.getLatestEuro()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        converterViewModel = ViewModelProvider(requireActivity()).get(ConverterViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConverterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}