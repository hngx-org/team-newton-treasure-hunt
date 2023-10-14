package com.teamnewton.treasurehunt.ui.admin

import com.teamnewton.treasurehunt.app.model.Game

data class TreasureDetailState(
    val game: Game = Game(),
    val isComplete: Boolean = false,
    val treasureAddStatus: Boolean = false,
    val updateTreasureStatus: Boolean = false,
    val selectedTreasure: Game? = null,
    val isBtnEnabled: Boolean = false,
)