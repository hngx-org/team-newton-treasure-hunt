package com.teamnewton.treasurehunt.ui.admin.treasuredetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTreasure(
    modifier: Modifier = Modifier,
    game: Game,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier.padding(8.dp),
                title = { Text(text = game.treasureHuntName) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                            )
                        },
                    )
                },
                actions = {
                    IconButton(
                        content = {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                        },
                        onClick = onEdit
                    )

                    IconButton(
                        content = {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        },
                        onClick = onDelete
                    )
                }
            )
        },
        content = { contentPadding ->
            Box(
                modifier = modifier.fillMaxSize().padding(contentPadding),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.papo_bg),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                    Column(
                        modifier = modifier
                            .verticalScroll(rememberScrollState())
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Spacer(modifier = modifier.height(15.dp))
                            Image(
                                painter = painterResource(id = R.drawable.treasure_box),
                                contentDescription = "",
                                modifier = modifier.size(150.dp)
                            )
                            Text(text = "Treasure Clue: ${game.treasureClue}")
                            Text(text = "Treasure Reward: ${game.treasureReward}")
                            Text(text = "Treasure Location: ${game.treasureLocationLat}")
                            Text(text = "Creation Date: ${formatDate(game.timestamp)}")
                        }
                    )
                }
            )
        }
    )
}