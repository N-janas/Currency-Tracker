package com.example.currency_tracker.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currency_tracker.Model.Entities.Favourites

@Dao
interface FavouritesDao {

    @Insert
    suspend fun insertFavourite(fav: Favourites)

    @Delete
    suspend fun deleteFavourite(fav: Favourites)

    @Query("SELECT * FROM favourites")
     fun allFavourites(): LiveData<List<Favourites>>
}