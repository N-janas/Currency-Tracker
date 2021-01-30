package com.example.currency_tracker.Model.Repositories

import androidx.lifecycle.LiveData
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.Model.FavouritesDao

class FavouritesRepository(private val favouritesDao: FavouritesDao) {

    suspend fun insertFavourite(favourites: Favourites){
        favouritesDao.insertFavourite(favourites)
    }

    suspend fun deleteFavourites(favourites: Favourites){
        favouritesDao.deleteFavourite(favourites)
    }

    fun selectAll(): LiveData<List<Favourites>> {
        return favouritesDao.allFavourites()
    }

    suspend fun isRowExist(fav: Favourites):Boolean {
        return favouritesDao.isRowExist(fav.currencyBaseId,fav.currencySymbolId)
    }
}