package com.teamnewton.treasurehunt.ui.admin.treasures

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.app.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminTreasuresScreen(
    modifier: Modifier = Modifier,
    onCreateTreasureHunt: () -> Unit,
    treasuresUIState: TreasuresUIState,
    onGameClick: (String) -> Unit,
    onBack: () -> Unit,
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Treasure Hunts") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "go back"
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(end = 16.dp, bottom = 16.dp),
                onClick = { onCreateTreasureHunt() },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        modifier = modifier.size(30.dp)
                    )
                }
            )
        },
        content = { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(12.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {

                            if (treasuresUIState.treasureList.isEmpty())
                                EmptyGamesView()
                            else if (treasuresUIState.isLoading)
                                CircularProgressIndicator()
                            else if (treasuresUIState.errorMsg != null)
                                Text(
                                    text = treasuresUIState.errorMsg,
                                    color = Color.Red,
                                    maxLines = 1
                                )
                            else
                                AdminGamesList(
                                    games = treasuresUIState.treasureList,
                                    onGameClick = onGameClick
                                )
                        }
                    )
                }
            )
        }


        @Composable
        fun AdminGamesList(
            modifier: Modifier = Modifier,
            games: List<Game>,
            onGameClick: (String) -> Unit,
        ) {
            LazyVerticalGrid(
                modifier = modifier.padding(8.dp),
                columns = GridCells.Fixed(2),
                content = {
                    items(games) { game ->
                        GameCard(
                            treasure = game,
                            onTreasureClick = onGameClick
                        )
                    }
                }
            )

        }