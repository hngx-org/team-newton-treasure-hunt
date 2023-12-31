package com.teamnewton.treasurehunt.app.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamnewton.treasurehunt.MainActivityViewModel
import com.teamnewton.treasurehunt.ui.admin.addtreasure.AddTreasureHunt
import com.teamnewton.treasurehunt.ui.admin.addtreasure.AddTreasureViewModel
import com.teamnewton.treasurehunt.ui.admin.treasuredetail.TreasureDetailViewModel
import com.teamnewton.treasurehunt.ui.admin.treasuredetail.ViewTreasure
import com.teamnewton.treasurehunt.ui.admin.treasures.AdminTreasuresScreen
import com.teamnewton.treasurehunt.ui.admin.treasures.TreasuresViewModel
import com.teamnewton.treasurehunt.ui.mainscreen.GameModeScreen
import com.teamnewton.treasurehunt.ui.maps.MapsScreen
import com.teamnewton.treasurehunt.ui.maps.getCityName
import com.teamnewton.treasurehunt.ui.onboarding.ProfileViewScreen
import com.teamnewton.treasurehunt.ui.onboarding.SplashScreen
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginScreen
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginViewModel
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignUpScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TreasureHuntAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    treasuresViewModel: TreasuresViewModel,
    addTreasureViewModel: AddTreasureViewModel,
    treasureDetailViewModel: TreasureDetailViewModel,
    mainActivityViewModel: MainActivityViewModel
) {

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = NestedRoutes.OnBoardingRoutes.route
    ) {
        onBoardingGraph(navController = navController, loginViewModel = loginViewModel)
        homeGraph(
            navController = navController,
            treasuresViewModel = treasuresViewModel,
            addTreasureViewModel = addTreasureViewModel,
            treasureDetailViewModel = treasureDetailViewModel,
            loginViewModel = loginViewModel,
            mainActivityViewModel = mainActivityViewModel
        )

    }
}


fun NavGraphBuilder.onBoardingGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {
    navigation(
        startDestination = OnBoardingRoutes.SplashScreen.route,
        route = NestedRoutes.OnBoardingRoutes.route
    ) {

        composable(route = OnBoardingRoutes.SplashScreen.route) {
            SplashScreen(
                onNext = {
                    navController.navigate(route = OnBoardingRoutes.SignInScreen.route) {
                        popUpTo(OnBoardingRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = OnBoardingRoutes.SignInScreen.route,
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
                    navController.navigate(NestedRoutes.MainRoutes.route) {
                        launchSingleTop = true
                        popUpTo(route = OnBoardingRoutes.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(OnBoardingRoutes.SignUpScreen.route) {
                        launchSingleTop = true
                        popUpTo(OnBoardingRoutes.SignInScreen.route) {
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
            route = OnBoardingRoutes.SignUpScreen.route,
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
            val context = LocalContext.current

            SignUpScreen(
                onNavigateToLogin = { navController.navigate(OnBoardingRoutes.SignInScreen.route) },
                loginState = state,
                signUp = { loginViewModel.createUser(context) },
                updateState = loginViewModel::updateLoginState,
                hasUser = loginViewModel.hasUser
            )
        }

        composable(
            route = OnBoardingRoutes.ProfileScreen.route,
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
                    navController.navigate(OnBoardingRoutes.SignInScreen.route)
                },
                userName = state.firstName ?: ""
            )

        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    treasuresViewModel: TreasuresViewModel,
    addTreasureViewModel: AddTreasureViewModel,
    treasureDetailViewModel: TreasureDetailViewModel,
    loginViewModel: LoginViewModel,
    mainActivityViewModel: MainActivityViewModel
) {
    navigation(
        startDestination = MainRoutes.GameModeScreen.route,
        route = NestedRoutes.MainRoutes.route
    ) {
        composable(
            route = MainRoutes.GameModeScreen.route,
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
                navToLoginPage = { navController.navigate(OnBoardingRoutes.SignInScreen.route) },
                onViewProfile = { navController.navigate(OnBoardingRoutes.ProfileScreen.route) },
                navToAdminScreen = { navController.navigate(MainRoutes.AdminModeScreen.route) },
                navToInstructions = { },
                navToPlayerScreen = { }

            )
        }

        composable(
            route = MainRoutes.AdminModeScreen.route,
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

            val treasures by treasuresViewModel.treasureList.collectAsState()

            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }
            val onGameClicked = remember {
                { gameId: String ->
                    navController.navigate(
                        MainRoutes.AdminViewTreasureScreen.route + "?id=$gameId"
                    ) {
                        launchSingleTop = true
                    }
                }
            }


            AdminTreasuresScreen(
                onCreateTreasureHunt = { navController.navigate(MainRoutes.AdminAddTreasureScreen.route + "?id={id}") },
                treasuresUIState = treasures,
                onGameClick = onGameClicked,
                onBack = onBack,
            )
        }

        composable(
            route = MainRoutes.AdminAddTreasureScreen.route + "?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            }),
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

            val gameId = it.arguments?.getString("id") as String

            LaunchedEffect(key1 = Unit, block = {
                addTreasureViewModel.getTreasure(gameId)
            })
            Log.i("TreasureGAMEID", gameId)

            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }
            val navToMap: () -> Unit = remember {
                {
                    navController.navigate(MainRoutes.AdminMapViewScreen.route)
                }
            }

            val saveTreasure: () -> Unit = remember {
                {
                    addTreasureViewModel.saveTreasure(gameId)
                    onBack()
                }
            }

            val state by addTreasureViewModel.treasure.collectAsState()
            val cityName = remember {
                mutableStateOf("")
            }
            LocalContext.current.getCityName(
                latitude =mainActivityViewModel.mlocation?.latitude ?:0.0,
                longitude = mainActivityViewModel.mlocation?.longitude ?:0.0
            ) { address ->
                cityName.value = address.locality
                Log.i("CityMAPSSCREEN",cityName.value)
            }

            AddTreasureHunt(
                onBack = onBack,
                game = state,
                updateGame = addTreasureViewModel::updateTreasureState,
                navigateToMap = navToMap,
                isEdit = gameId.isNotBlank(),
                isBtnEnabled = addTreasureViewModel.treasureDetail.collectAsState().value.isBtnEnabled,
                saveTreasure = saveTreasure,
                locationName = cityName.value
            )
        }

        composable(
            route = MainRoutes.AdminViewTreasureScreen.route + "?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            }),
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

            val gameId = it.arguments?.getString("id") as String

            LaunchedEffect(key1 = Unit, block = {
                treasureDetailViewModel.getTreasure(gameId)
            })

            val game by treasureDetailViewModel.treasure.collectAsState()

            val onBack: () -> Unit = remember {
                {
                    navController.navigateUp()
                }
            }

            ViewTreasure(
                game = game,
                onBack = onBack,
                onEdit = { navController.navigate(MainRoutes.AdminAddTreasureScreen.route + "?id=$gameId") },
                onDelete = {
                    treasureDetailViewModel.deleteTreasure(gameId)
                    onBack()
                }
            )
        }

        composable(
            route = MainRoutes.AdminMapViewScreen.route,
        ) {
            MapsScreen(
                context = LocalContext.current,
                locationViewModel = mainActivityViewModel
            )

        }

    }
}