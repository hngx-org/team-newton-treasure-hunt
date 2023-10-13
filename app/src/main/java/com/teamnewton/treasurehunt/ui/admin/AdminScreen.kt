package com.teamnewton.treasurehunt.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.theme.Pink40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    modifier: Modifier = Modifier,
    onCreateTreasureHunt: () -> Unit,
    treasureGames: List<Game>,
    onGameClick: (Game) -> Unit,
    onBack: () -> Unit,
) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Admin Player") },
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
                        painter = painterResource(id = R.drawable.baseline_videogame),
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
                content = {
                    if (treasureGames.isEmpty())
                        EmptyGamesView()
                    else
                        AdminGamesList(
                            games = treasureGames,
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
    onGameClick: (Game) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
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