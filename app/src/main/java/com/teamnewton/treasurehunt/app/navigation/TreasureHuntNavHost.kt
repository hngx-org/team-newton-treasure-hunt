package com.teamnewton.treasurehunt.app.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.teamnewton.treasurehunt.ui.onboarding.ProfileViewScreen
import com.teamnewton.treasurehunt.ui.onboarding.SplashScreen
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginScreen
import com.teamnewton.treasurehunt.ui.onboarding.signup.GoogleAuthUIClient
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignInViewModel
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignUpScreen
import kotlinx.coroutines.launch

@Composable
fun TreasureHuntAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    googleAuthUIClient: GoogleAuthUIClient
) {
    val scope = rememberCoroutineScope()

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashScreen.route
    ) {


        composable(route = SplashScreen.route) {
            SplashScreen(
                onNext = {
                    navController.navigate(route = SignInScreen.route) {
                        popUpTo(SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = SignInScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            LoginScreen()

        }
        composable(route = SignUpScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsState()

            val context = LocalContext.current
            LaunchedEffect(key1 = Unit) {
                if (googleAuthUIClient.getSignedInUser() != null) {
                    navController.navigate("profile")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUIClient.getSignInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful, block = {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "SUCCESSFUL LOGIN", Toast.LENGTH_LONG)
                        .show()
                    navController.navigate(ProfileScreen.route)
                    viewModel.resetState()
                }
            }
            )

            SignUpScreen(
                state = state,
                onSignInClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable(route = ProfileScreen.route) {
            val context = LocalContext.current

            googleAuthUIClient.getSignedInUser()?.let { userData ->
                ProfileViewScreen(
                    userData = userData,
                    onSignOut = {
                        scope.launch {
                            googleAuthUIClient.signOut()
                            Toast.makeText(
                                context,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.navigate(SignUpScreen.route)
                        }
                    }
                )
            }
        }
    }
}