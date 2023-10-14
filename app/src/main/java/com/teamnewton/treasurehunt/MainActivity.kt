package com.teamnewton.treasurehunt

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.teamnewton.treasurehunt.app.navigation.TreasureHuntAppNavHost
import com.teamnewton.treasurehunt.app.theme.TreasureHuntTheme
import com.teamnewton.treasurehunt.ui.admin.addtreasure.AddTreasureViewModel
import com.teamnewton.treasurehunt.ui.admin.treasuredetail.TreasureDetailViewModel
import com.teamnewton.treasurehunt.ui.admin.treasures.TreasuresViewModel
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val locationViewModel: MainActivityViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TreasureHuntTheme {
                val loginViewModel = viewModel<LoginViewModel>()
                val addTreasureViewModel = viewModel<AddTreasureViewModel>()
                val treasureViewModel = viewModel<TreasuresViewModel>()
                val treasureDetailViewModel = viewModel<TreasureDetailViewModel>()
                TreasureHuntAppNavHost(
                    navController = rememberNavController(),
                    loginViewModel = loginViewModel,
                    addTreasureViewModel = addTreasureViewModel,
                    treasuresViewModel = treasureViewModel,
                    treasureDetailViewModel = treasureDetailViewModel,
                    mainActivityViewModel = locationViewModel
                )
            }
        }
    }
}