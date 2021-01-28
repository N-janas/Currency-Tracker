package com.example.currency_tracker.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import com.example.currency_tracker.Model.Repositories.ResponsesRepository
import kotlinx.coroutines.launch
import okhttp3.Response
import java.util.*
import kotlin.math.round

class ConverterViewModel(application: Application): AndroidViewModel(application) {

    // Result of conversion, reacting to change when data is loaded
    private var _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String>
        get() = _conversionResult



    fun getLatestRatesBetweenTwo(symbol: String, base: String, baseAmount: Double){
        viewModelScope.launch {
            val rate = ResponsesRepository.getLatestRateBetweenTwo(symbol, base)
            _conversionResult.value = "%.2f".format(baseAmount * rate)
        }
    }

}