package com.example.currency_tracker.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.Entities.Favourites
import java.util.*

@Database(
    entities = [Currency::class, Favourites::class],
    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun favouritesDao(): FavouritesDao

    companion object{
        @Volatile
        private var INSTANCE: ProjectDatabase ?= null

        fun getDatabase(context: Context): ProjectDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectDatabase::class.java,
                        "Project_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                    return  instance
                }
            }
        }
    }
}