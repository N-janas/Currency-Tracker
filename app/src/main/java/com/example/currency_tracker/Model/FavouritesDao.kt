package com.example.currency_tracker.Model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.currency_tracker.Model.Entities.Favourites

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(fav: Favourites)

    @Delete
    suspend fun deleteFavourite(fav: Favourites)

    @Query("SELECT * FROM favourites")
     fun allFavourites(): LiveData<List<Favourites>>
}