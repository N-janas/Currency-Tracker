package com.example.currency_tracker.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import kotlinx.coroutines.launch

class ConverterViewModel(application: Application): AndroidViewModel(application) {

    fun getLatestEuro(){
        viewModelScope.launch {
            Log.d("logs", CurrencyRepository.getLatestEuro().toString())
        }
    }
}