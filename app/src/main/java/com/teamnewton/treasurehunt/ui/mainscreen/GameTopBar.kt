package com.teamnewton.treasurehunt.ui.mainscreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    modifier: Modifier = Modifier,
    onViewProfile: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = "Game Mode") },
        actions = {
            IconButton(
                content = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "")
                },
                onClick = onViewProfile
            )

        }
    )

}