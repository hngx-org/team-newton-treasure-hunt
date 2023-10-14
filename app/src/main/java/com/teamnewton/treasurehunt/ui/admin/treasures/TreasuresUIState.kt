package com.teamnewton.treasurehunt.ui.admin.treasures

import com.teamnewton.treasurehunt.app.model.Game
import com.teamnewton.treasurehunt.app.repository.NetworkResults

data class TreasuresUIState(
    val treasureList: List<Game> = emptyList(),
    val isLoading: Boolean = true,
    val errorMsg: String? = null,
)
