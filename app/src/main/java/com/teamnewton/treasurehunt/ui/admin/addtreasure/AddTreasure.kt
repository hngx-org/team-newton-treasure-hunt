package com.teamnewton.treasurehunt.ui.admin.addtreasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTreasureHunt(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    game: Game,
    updateGame: (Game) -> Unit,
    navigateToMap: () -> Unit,
    isEdit: Boolean,
    saveTreasure: () -> Unit,
    isBtnEnabled: Boolean,
    locationName: String,
) {

    val topText = if (isEdit) "Edit Treasure Hunt" else "Create Treasure Hunt"



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier,
                title = { Text(text = topText) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                            )
                        },
                    )
                })
        },
        content = { scaffoldPadding ->
            Box(
                modifier = modifier.fillMaxSize(),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.papo_bg),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                    Column(
                        modifier = modifier
                            .padding(scaffoldPadding)
                            .verticalScroll(rememberScrollState())
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        content = {

                            OutlinedTextField(
                                value = game.treasureHuntName,
                                onValueChange = { updateGame(game.copy(treasureHuntName = it)) },
                                label = { Text(text = "Treasure Hunt Name") },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                modifier = modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = game.treasureClue,
                                onValueChange = { updateGame(game.copy(treasureClue = it)) },
                                label = { Text(text = "Treasure Hunt Clue") },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                modifier = modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = game.treasureReward,
                                onValueChange = { updateGame(game.copy(treasureReward = it)) },
                                label = { Text(text = "Treasure Hunt Reward") },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                modifier = modifier.fillMaxWidth()
                            )

                            TextButton(
                                onClick = navigateToMap,
                                content = {
                                    Text(text = "Add Treasure Location", fontSize = 18.sp)
                                }
                            )
                            Text(text = "Current Location: ${locationName}", fontSize = 18.sp)

                            Spacer(modifier = modifier.height(10.dp))

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .align(Alignment.CenterHorizontally),
                                enabled = isBtnEnabled,
                                onClick = saveTreasure,
                                content = {
                                    Text(text = if (isEdit) "Update" else "Save")
                                }
                            )
                        }
                    )
                }
            )

        }
    )
}