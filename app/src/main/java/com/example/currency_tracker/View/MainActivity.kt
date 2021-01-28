package com.example.currency_tracker.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import com.example.currency_tracker.R
import kotlinx.android.synthetic.main.activity_main.*

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
//        val checkRatesRequest: WorkRequest =
//            PeriodicWorkRequestBuilder<MyWorker>(5, TimeUnit.MINUTES)
//                .setConstraints(constraints)
//                .setBackoffCriteria(
//                    BackoffPolicy.LINEAR,
//                    10,
//                    TimeUnit.SECONDS)
//                .build()


        //Zakomentowałem to bo mi wgle nie ruszało P.J
//        btnWelcomeToConversion.setOnClickListener {
//            val checkRatesRequest: WorkRequest =
//                OneTimeWorkRequestBuilder<MyWorker>()
//                    .setConstraints(constraints)
//                    .build()
//            // Enqueue scheduled work
//            WorkManager.getInstance(applicationContext).enqueue(checkRatesRequest)
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

