package com.example.currency_tracker.View.MainActivityPackage

import androidx.navigation.NavController

interface MySideMenu {
    fun lockDrawer()
    fun unlockDrawer()
    fun getNavController():NavController
}