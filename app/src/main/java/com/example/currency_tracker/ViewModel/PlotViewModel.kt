package com.example.currency_tracker.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.currency_tracker.Model.ProjectDatabase
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import java.util.*

const val MILLIS_IN_MONTH: Long = 1000 * 60 * 60 * 24 * 31L

class PlotViewModel(application: Application) : AndroidViewModel(application)
{
    private val repository: CurrencyRepository

    init
    {
        val currencyDao = ProjectDatabase.getDatabase(application).currencyDao()
        repository = CurrencyRepository(currencyDao)
    }


    // funckja aktora zwraca dane do wykresu
    fun getDataForPlot(base: String) = repository.getListOfCurrency(
            base = base,
            startAt = Date(System.currentTimeMillis() - MILLIS_IN_MONTH),
            endAt = Date(System.currentTimeMillis())
    )
}