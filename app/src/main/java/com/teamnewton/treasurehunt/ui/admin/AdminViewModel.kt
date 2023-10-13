package com.teamnewton.treasurehunt.ui.admin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdminViewModel: ViewModel() {

    private val _treasure = MutableStateFlow(Game())
    val treasure = _treasure.asStateFlow()

    fun updateTreasureState( state: Game) {
        _treasure.update { state }
    }


}