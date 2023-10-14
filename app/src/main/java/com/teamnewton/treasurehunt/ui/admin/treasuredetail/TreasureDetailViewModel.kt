package com.teamnewton.treasurehunt.ui.admin.treasuredetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamnewton.treasurehunt.app.model.Game
import com.teamnewton.treasurehunt.app.repository.StorageRepository
import com.teamnewton.treasurehunt.ui.admin.TreasureDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreasureDetailViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {

    private val _treasureDetail = MutableStateFlow(TreasureDetailState())
    val treasureDetail = _treasureDetail.asStateFlow()

    private val _treasure = MutableStateFlow(Game())
    val treasure = _treasure.asStateFlow()

    private var _deletedTreasureStatus = MutableStateFlow(false)

    fun getTreasure(treasureHuntId: String) {
        viewModelScope.launch {
            when (treasureHuntId) {
                "" -> {
                    resetState()
                }

                else -> {
                    repository.getTreasureHunt(
                        treasurehuntId = treasureHuntId,
                        onError = { },
                        onSuccess = { game ->
                            _treasureDetail.update { it.copy(selectedTreasure = game) }
                            _treasureDetail.update { it.copy(game = game!!) }
                            _treasure.update { game!! }
                        }
                    )
                }
            }
        }
    }

    fun deleteTreasure(treasureHuntId: String) {
        viewModelScope.launch {
            repository.deleteTreasure(
                treasureHuntId,
                onComplete = { status ->
                    _deletedTreasureStatus.value = status
                }
            )
        }
    }

    fun resetState() {
        _treasure.update { Game() }
        _treasureDetail.update { TreasureDetailState() }
    }

}