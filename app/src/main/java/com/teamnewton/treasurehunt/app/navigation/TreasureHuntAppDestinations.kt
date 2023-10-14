package com.teamnewton.treasurehunt.app.navigation

sealed class NestedRoutes(val route: String) {
    object OnBoardingRoutes: NestedRoutes("onboard")
    object MainRoutes: NestedRoutes("main")
}

sealed class OnBoardingRoutes(val route: String) {
    object SplashScreen: OnBoardingRoutes("splash")
    object SignInScreen: OnBoardingRoutes("sign_in")
    object SignUpScreen: OnBoardingRoutes("sign_up")

    object ProfileScreen: OnBoardingRoutes("profile")

}

sealed class MainRoutes(val route: String) {
    object GameModeScreen: MainRoutes("game_mode")
    object AdminModeScreen: MainRoutes("admin_mode")
    object AdminAddTreasureScreen: MainRoutes("admin_add_treasure")
    object AdminViewTreasureScreen: MainRoutes("admin_view_treasure")
    object AdminMapViewScreen: MainRoutes("admin_map_view_screen")

}
