package com.example.currency_tracker.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.Model.ProjectDatabase
import com.example.currency_tracker.Model.Repositories.FavouritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavouritesRepository
    val readAllData: LiveData<List<Favourites>>

    init {
        val favouritesDao = ProjectDatabase.getDatabase(application).favouritesDao()
        repository = FavouritesRepository(favouritesDao)
        readAllData = favouritesDao.allFavourites()
    }

    fun addFavourites(favourite: Favourites){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavourite(favourite)
        }
    }

    fun deleteFavourites(favourite: Favourites){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavourites(favourite)
        }
    }

    fun alreadyNotExist(fav: Favourites): Boolean{
        Log.d("Fav","Inside view model allData: "+readAllData.value)
        return readAllData.value?.any{obj-> obj.currencyBaseId==fav.currencyBaseId && obj.currencySymbolId==fav.currencySymbolId} != true
    }
}