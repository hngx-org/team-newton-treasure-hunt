package com.teamnewton.treasurehunt.app.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.teamnewton.treasurehunt.ui.admin.AddTreasureHunt
import com.teamnewton.treasurehunt.ui.admin.AdminScreen
import com.teamnewton.treasurehunt.ui.admin.AdminViewModel
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
            //val viewModel = viewModel<LoginViewModel>()
            val state by loginViewModel.loginState.collectAsState()
            val context = LocalContext.current

            SignUpScreen(
                onNavigateToLogin = { navController.navigate(SignInScreen.route) },
                loginState = state,
                signUp = { loginViewModel.createUser(context) },
                updateState = loginViewModel::updateLoginState,
                hasUser = loginViewModel.hasUser
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
            val state by loginViewModel.loginState.collectAsState()
            ProfileViewScreen(
                onSignOut = {
                    loginViewModel.signOut()
                    loginViewModel.resetState()
                    navController.navigate(SignInScreen.route)
                },
                userName = state.firstName ?: ""
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
                onViewProfile = { navController.navigate(ProfileScreen.route) },
                navToAdminScreen = { navController.navigate(AdminModeScreen.route) },
                navToInstructions = { },
                navToPlayerScreen = { }

            )
        }

        composable(
            route = AdminModeScreen.route,
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
            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }

            AdminScreen(
                onCreateTreasureHunt = { navController.navigate(AdminAddTreasureScreen.route) },
                treasureGames = emptyList(),
                onGameClick = { },
                onBack = onBack
            )
        }

        composable(
            route = AdminAddTreasureScreen.route,
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
            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }
            val viewModel = viewModel<AdminViewModel>()
            val state by viewModel.treasure.collectAsState()

            AddTreasureHunt(
                onBack = onBack,
                game = state,
                updateGame = viewModel::updateTreasureState,
                navigateToMap = { }
            )
        }
    }
}