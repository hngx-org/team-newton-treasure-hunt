package com.teamnewton.treasurehunt.ui.admin.addtreasure

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.teamnewton.treasurehunt.app.model.Game
import com.teamnewton.treasurehunt.app.repository.StorageRepository
import com.teamnewton.treasurehunt.ui.admin.TreasureDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTreasureViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {

    private val _treasure = MutableStateFlow(Game())
    val treasure = _treasure.asStateFlow()

    private val hasUser = repository.user()!=null
    private val user = repository.user()


    private val _treasureDetail = MutableStateFlow(TreasureDetailState())
    val treasureDetail = _treasureDetail.asStateFlow()

    fun updateTreasureState(state: Game) {
        val isBtnEnabled = state.treasureHuntName.isNotBlank() && state.treasureClue.isNotBlank()
                && state.treasureReward.isNotBlank()
        _treasure.update { state }
        _treasureDetail.update { it.copy(game = state) }
        _treasureDetail.update { it.copy(isBtnEnabled = isBtnEnabled) }
    }


    private fun addTreasure() {
        viewModelScope.launch {
            if (hasUser) {
                repository.addTreasureHunt(
                    userId = user!!.uid,
                    treasureHuntName = _treasure.value.treasureHuntName,
                    treasureClue = _treasure.value.treasureClue,
                    treasureReward = _treasure.value.treasureReward,
                    treasureLocationLong = _treasure.value.treasureLocationLong,
                    treasureLocationLat = _treasure.value.treasureLocationLat,
                    timestamp = Timestamp.now(),
                    onComplete = { isComplete ->
                        _treasureDetail.update { it.copy(treasureAddStatus = isComplete) }
                    }
                )
            }
        }
    }


    //addtreasure
    fun getTreasure(treasureHuntId: String) {
        viewModelScope.launch {
            when (treasureHuntId) {
                "" -> { resetState() }
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

    //addTreasure
    private fun updateTreasure(
        treasureHuntId: String
    ) {
        viewModelScope.launch {
            repository.updateTreasure(
                treasurehuntId = treasureHuntId,
                treasureHuntName = _treasure.value.treasureHuntName,
                treasureClue = _treasure.value.treasureClue,
                treasureReward = _treasure.value.treasureReward,
                treasureLocationLat = _treasure.value.treasureLocationLat,
                treasureLocationLong = _treasure.value.treasureLocationLong,
                onResult = { status ->
                    _treasureDetail.update { it.copy(updateTreasureStatus = status) }
                }
            )
        }
    }


    fun resetTreasureAddedStatus() {
        _treasureDetail.update {
            it.copy(
                treasureAddStatus = false,
                updateTreasureStatus = false
            )
        }
    }

    //addtreasure
    private fun resetState() {
        _treasure.update { Game() }
        _treasureDetail.update { TreasureDetailState() }
    }


    fun saveTreasure(treasureHuntId: String) {
        viewModelScope.launch {
            Log.i("ADD TREASUREvm", treasureHuntId)
            if (treasureHuntId.isNotBlank()) {
                updateTreasure(treasureHuntId)
            }
            else {
                addTreasure()
            }
        }
    }


}