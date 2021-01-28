package com.example.currency_tracker.View

import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.navigation.NavController
import com.example.currency_tracker.R

class SideMenuLogic(private val mySideMenu: MySideMenu) {
    private var base: MenuItem? = null
    private var symbol: MenuItem? = null
    private var originalBaseIcon: Drawable? = null
    private var iconForSelect: Int = R.drawable.ic_baseline_arrow_forward_24

    fun selectItem(item: MenuItem){
        if(isNotSelected(item)){
            setArgument(item)
        }
        else{
            unselectItem(item)
        }
    }

    private fun isNotSelected(item: MenuItem): Boolean {
        if(item.title != base?.title){
            return true
        }
        return false
    }

    private fun setArgument(item: MenuItem) {
        if(base==null){
            setFirstArgument(item)
        }
        else{
            setSecondArgumentAndNavigateToConverter(item)
        }
    }

    private fun setSecondArgumentAndNavigateToConverter(item: MenuItem) {
        symbol = item
        val action = NewWelcomeFragmentDirections
                .actionNewWelcomeFragmentToNewConverterFragment(
                        base!!.title.toString(),
                        symbol!!.title.toString()
                )
        mySideMenu.getNavController().navigate(action)
        mySideMenu.lockDrawer()
        restoreToDefault()
    }

    private fun setFirstArgument(item: MenuItem) {
        originalBaseIcon = item.icon
        base = item
        base?.setIcon(iconForSelect)
    }

    private fun restoreToDefault() {
        base?.icon = originalBaseIcon
        base = null
        symbol = null
    }

    private fun unselectItem(item: MenuItem) {
        base = null
        item.icon = originalBaseIcon
    }

}