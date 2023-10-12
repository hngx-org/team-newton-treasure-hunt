package com.teamnewton.treasurehunt.app.navigation

interface TreasureHuntAppDestinations {
    val route: String
}

object SplashScreen : TreasureHuntAppDestinations {
    override val route = "splash"
}

object SignInScreen : TreasureHuntAppDestinations {
    override val route = "sign_in"
}

object SignUpScreen : TreasureHuntAppDestinations {
    override val route = "sign_up"
}

object ProfileScreen : TreasureHuntAppDestinations {
    override val route = "profile"
}
object ARScreen : TreasureHuntAppDestinations {
    override val route = "ar_screen"
}

object GameModeScreen: TreasureHuntAppDestinations {
    override val route = "game_mode"
}
