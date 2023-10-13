package com.teamnewton.treasurehunt.ui.admin



data class Game(
    val treasureHuntName: String = "",
    val treasureLocationLat: Double = 0.0,
    val treasureLocationLong: Double = 0.0,
    val treasureClue:String = "",
    val treasureReward: String = "",
)