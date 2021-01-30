package com.example.currency_tracker.View.Converter.ConverterHelperClasses

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.airbnb.lottie.LottieAnimationView
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.R
import com.example.currency_tracker.ViewModel.FavouritesViewModel
import kotlinx.android.synthetic.main.new_fragment_converter.view.*

class FavouriteButtonLogic(private val view: View, activity: ViewModelStoreOwner):ICurrencyFavourite {
    private val baseSpinner: Spinner = view.new_converter_baseSpinner
    private val symbolSpinner: Spinner = view.new_converter_symbolSpinner
    private val favouriteButton: LottieAnimationView = view.new_converter_favoriteButton
    private var favouritesViewModel: FavouritesViewModel = ViewModelProvider(activity).get(FavouritesViewModel::class.java)
    fun addToFavourites(){

        val fav = Favourites(baseSpinner.selectedItemPosition,symbolSpinner.selectedItemPosition)

        if(favouritesViewModel.alreadyNotExist(fav)){
            favouriteButton.playAnimation()
            favouritesViewModel.addFavourites(fav)

        }
        else{
            favouritesViewModel.deleteFavourites(fav)
            favouriteButton.playAnimation()
        }
    }

    fun checkIsOnFavouriteList() {
        val fav = Favourites(view.new_converter_baseSpinner.selectedItemPosition,view.new_converter_symbolSpinner.selectedItemPosition)
        if(favouritesViewModel.alreadyNotExist(fav)){
            favouriteButton.scaleY = 1f
            favouriteButton.progress= 0f
        }
        else{
           favouriteButton.progress= 1f
           favouriteButton.scaleY = -1f
           favouriteButton.reverseAnimationSpeed()
        }
    }

    override fun refreshView() {
        checkIsOnFavouriteList()
    }
}