package com.teamnewton.treasurehunt.app.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.teamnewton.treasurehunt.ui.ar.ARScreen
import com.teamnewton.treasurehunt.ui.mainscreen.GameModeScreen
import com.teamnewton.treasurehunt.ui.onboarding.ProfileViewScreen
import com.teamnewton.treasurehunt.ui.onboarding.SplashScreen
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginScreen
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginViewModel
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignUpScreen

@Composable
fun TreasureHuntAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {

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
            val context = LocalContext.current
            val state = loginViewModel.loginState.collectAsState().value
            LoginScreen(
                updateState = loginViewModel::updateLoginState,
                onNavigateToGameMode = {
                    navController.navigate(GameModeScreen.route) {
                        // launchSingleTop = true
                        //popUpTo(route = SignInScreen.route) {
                        //   inclusive = true
                        //}
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(SignUpScreen.route) {
                        launchSingleTop = true
                        popUpTo(SignInScreen.route) {
                            inclusive = true
                        }
                    }
                },
                logIn = {
                    loginViewModel.loginUser(context)
                },
                loginState = state,
                hasUser = loginViewModel.hasUser
            )

        }
        composable(
            route = SignUpScreen.route,
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
            val viewModel = viewModel<LoginViewModel>()
            val state by viewModel.loginState.collectAsState()
            val context = LocalContext.current

            SignUpScreen(
                onNavigateToLogin = { navController.navigate(SignInScreen.route) },
                loginState = state,
                signUp = { viewModel.createUser(context) },
                updateState = viewModel::updateLoginState,
                hasUser = viewModel.hasUser
            )
        }

        composable(
            route = ProfileScreen.route,
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
            ProfileViewScreen(
                onSignOut = {
                    loginViewModel.signOut()
                    loginViewModel.resetState()
                    navController.navigate(SignInScreen.route)
                }
            )

        }

        composable(route = ARScreen.route) {
            ARScreen()
        }
        composable(
            route = GameModeScreen.route,
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
            }) {
            GameModeScreen(
                hasUser = loginViewModel.hasUser,
                navToLoginPage = { navController.navigate(SignInScreen.route) },
                onViewProfile = { navController.navigate(ProfileScreen.route) }

            )
        }
    }
}