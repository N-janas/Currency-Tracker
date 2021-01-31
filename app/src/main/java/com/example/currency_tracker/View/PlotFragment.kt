package com.example.currency_tracker.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.R
import com.example.currency_tracker.View.Converter.ConverterFragmentArgs
import com.example.currency_tracker.ViewModel.PlotViewModel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_plot.*
import java.util.*


class PlotFragment : Fragment(R.layout.fragment_plot)
{
    private lateinit var viewModel: PlotViewModel

    private val args by navArgs<PlotFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
                ViewModelProvider(requireActivity()).get(PlotViewModel::class.java)

        makePlot(args.base, args.second)
        setSelectionInSpinner()

        // usuniecie osi X która pomimo podania w DataPoint <Date, Double> wyswietla jakis syf
        graph.gridLabelRenderer.isHorizontalLabelsVisible = false;

        setupClickListeners()
    }

    private var liveData: LiveData<List<Currency>>? = null
    private var observer: Observer<List<Currency>>? = null


    // zrobienie listenerow, kazda zmiania waluty w spinerzre wywola zrobienie nowego plotu
    private fun setupClickListeners()
    {
        val listener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            )
            {
                makePlot(
                        base = plot_baseSpinner.selectedItem.toString(),
                        second = plot_symbolSpinner.selectedItem.toString()
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                Log.d("MyTag", "Nothing selected, do nothing")
            }

        }

        plot_symbolSpinner.onItemSelectedListener = listener
        plot_baseSpinner.onItemSelectedListener = listener
    }

    // funckja ktora robi plot. pobranie live daty z viewmodel i na tej podstawie zrobienie danych
    // trzeba tez anulowac obserwacje poprzednej livedaty jesli byla.
    // ogolnie powinno to byc w viewmodelu wszystko a ui powinien dostac gotową datę ale tak bylo szybciej
    private fun makePlot(base: String, second: String)
    {
        observer?.let {
            liveData?.removeObserver(it)
        }

        liveData = viewModel.getDataForPlot(base)

        observer = Observer<List<Currency>> { currencies ->

            Log.d("MyTag", "Observed change")

            val data = arrayListOf<DataPoint>()

            currencies.forEach { currency ->
                currency.rates[second]?.let { rate ->
                    Log.d("MyTag", rate.toString())
                    data.add(DataPoint(currency.date, rate))
                }
            }

            val series: LineGraphSeries<DataPoint> = LineGraphSeries(data.toTypedArray())

            graph.series.clear()
            graph.addSeries(series)
        }

        liveData?.observe(viewLifecycleOwner, observer!!)
    }

    // po wejsciu w fragment trzeba ustwic item w spinerze na odpowiedni taki jaki byl wybrany w poprzednim fragmncie
    private fun setSelectionInSpinner()
    {
        Log.d("MyTag", args.base)

        for (i in 0..plot_baseSpinner.adapter.count)
        {
            val x = plot_baseSpinner.adapter.getItem(i) as String
            if (x == args.base)
            {
                plot_baseSpinner.setSelection(i)
                break
            }
        }

        Log.d("MyTag", args.second)


        for (i in 0..plot_symbolSpinner.adapter.count)
        {
            val x = plot_baseSpinner.adapter.getItem(i) as String
            if (x == args.second)
            {
                plot_symbolSpinner.setSelection(i)
                break
            }
        }
    }

}