package com.teamnewton.treasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamnewton.treasurehunt.ui.ar.ARScreen
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.teamnewton.treasurehunt.app.navigation.TreasureHuntAppNavHost
import com.teamnewton.treasurehunt.app.theme.TreasureHuntTheme
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginViewModel
import com.teamnewton.treasurehunt.ui.onboarding.signup.GoogleAuthUIClient

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreasureHuntTheme {
                val viewModel = viewModel<LoginViewModel>()
                TreasureHuntAppNavHost(
                    navController = rememberNavController(),
                    loginViewModel = viewModel
                )
            }
        }
    }
}