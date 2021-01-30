package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.airbnb.lottie.LottieAnimationView
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.ViewModel.FavouritesViewModel
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class FavouriteButtonLogic(private val view: View, activity: ViewModelStoreOwner) {
    private val baseSpinner: Spinner = view.new_converter_baseSpinner
    private val symbolSpinner: Spinner = view.new_converter_symbolSpinner
    private val favouriteButton: LottieAnimationView = view.new_converter_favoriteButton
    private var favouritesViewModel: FavouritesViewModel = ViewModelProvider(activity).get(FavouritesViewModel::class.java)
    fun addToFavourites(){

        val fav = Favourites(baseSpinner.selectedItemPosition,symbolSpinner.selectedItemPosition)

        Log.d("Fav","Base: ${baseSpinner.selectedItemPosition} ")
        Log.d("Fav","Symbol: ${symbolSpinner.selectedItemPosition} ")

        if(favouritesViewModel.alreadyNotExist(fav)){
            Log.d("Fav","Heja")
            favouriteButton.progress = 0f
            favouriteButton.pauseAnimation()
            favouriteButton.playAnimation()
            favouritesViewModel.addFavourites(fav)
            Toast.makeText(view.context,"Dodano do ulubionych",Toast.LENGTH_SHORT).show()
        }
        else{
            favouritesViewModel.deleteFavourites(fav)
            Toast.makeText(view.context,"Usunięto z ulubionych",Toast.LENGTH_SHORT).show()
        }
    }
}