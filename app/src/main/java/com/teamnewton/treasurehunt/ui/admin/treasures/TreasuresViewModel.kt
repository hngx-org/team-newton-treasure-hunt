package com.teamnewton.treasurehunt.ui.admin.treasures

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamnewton.treasurehunt.app.repository.NetworkResults
import com.teamnewton.treasurehunt.app.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreasuresViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {

    private val _treasureList = MutableStateFlow(TreasuresUIState())
    val treasureList = _treasureList.asStateFlow()


    val hasUser: Boolean = repository.user() !=null

    private val userId = repository.getUserId()

    init {
        loadTreasures()
    }

    private fun loadTreasures() {
        if (hasUser) {
            if (userId.isNotBlank()) {
                getTreasures(userId)
            }
        } else {
            _treasureList.update { it.copy(errorMsg = "User Not Logged In.") }
        }
    }

    private fun getTreasures(userId: String) {
        viewModelScope.launch {
            repository.getTreasuresHunts(userId).collect { result ->
                _treasureList.update { state ->
                    when (result) {
                        is NetworkResults.Error -> {
                            state.copy(
                                errorMsg = result.throwable?.message ?: "User Not Logged In.",
                                isLoading = false,
                                treasureList = emptyList()
                            )
                        }

                        is NetworkResults.Loading -> {
                            state.copy(isLoading = true, treasureList = emptyList())
                        }

                        is NetworkResults.Success -> {
                            state.copy(
                                treasureList = result.data ?: emptyList(),
                                isLoading = false,
                                errorMsg = null
                            )
                        }
                    }
                }
            }

        }
    }
}