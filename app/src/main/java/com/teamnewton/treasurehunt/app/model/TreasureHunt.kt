package com.teamnewton.treasurehunt.app.model

import com.google.firebase.Timestamp

//todo a game can be solved or not solved
data class Game(
    val userId: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val documentId: String = "",
    val treasureHuntName: String = "",
    val treasureLocationLat: Double = 0.0,
    val treasureLocationLong: Double = 0.0,
    val treasureClue:String = "",
    val treasureReward: String = "",
)