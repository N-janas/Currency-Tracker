package com.example.currency_tracker.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.currency_tracker.Model.MyWorker
import com.example.currency_tracker.R
import java.util.concurrent.TimeUnit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import com.example.currency_tracker.Model.ProjectDatabase
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import com.example.currency_tracker.View.MainActivityPackage.MySideMenu
import com.example.currency_tracker.View.MainActivityPackage.SideMenuLogic

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_fragment_welcome.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MySideMenu {
    private lateinit var sideMenuLogic : SideMenuLogic
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewForSideMenu()
        initializeLogicForSideMenu(this)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.itemEUR -> sideMenuLogic.selectItem(it)
                R.id.itemUSD -> sideMenuLogic.selectItem(it)
                R.id.itemPLN -> sideMenuLogic.selectItem(it)
                //tu będą kolejne waluty
            }
            true
        }

        // Create constraints that will make internet connection required
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Setting up periodic work schedule
        val checkRatesRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<MyWorker>(24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "apiCallRequest",
            ExistingPeriodicWorkPolicy.KEEP,
            checkRatesRequest
        )

//        val currencyRepository: CurrencyRepository =
//            CurrencyRepository(ProjectDatabase.getDatabase(applicationContext).currencyDao())
//        // DEBUG for testing if db get date which it suppose to get (i.e no repetitions)
//        new_welcome_addButton.setOnClickListener {
//
//            lifecycleScope.launch {
//                Log.d("dbLog", "Dates in DB : " + currencyRepository.debugGetDatesInDb()
//                    .joinToString(", "))
//                for (d in currencyRepository.debugGetDatesInDb()){
//                    Log.d("dbLog", currencyRepository.debugGetCurrenciesByDate(d)
//                        .joinToString(", "))
//                }
//            }
//        }
    }

    private fun initializeLogicForSideMenu(mySideMenu: MySideMenu) {
        sideMenuLogic = SideMenuLogic(mySideMenu)
    }

    private fun initializeViewForSideMenu() {
        navController = findNavController(R.id.fragNavigation)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
        enableColoredIcons()
    }

    private fun enableColoredIcons() {
        navView.itemIconTintList = null
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragNavigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun getNavController(): NavController {
        return navController
    }
}

