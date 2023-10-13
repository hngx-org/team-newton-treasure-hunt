package com.teamnewton.treasurehunt.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.ui.onboarding.signup.bounceClick

@Composable
fun GameModeScreen(
    modifier: Modifier = Modifier,
    onViewProfile: () -> Unit,
    hasUser: Boolean,
    navToLoginPage: () -> Unit,
    navToAdminScreen: () -> Unit,
    navToPlayerScreen: () -> Unit,
    navToInstructions: () -> Unit,
) {
    LaunchedEffect(key1 = Unit){
        if (!hasUser){
            navToLoginPage.invoke()
        }
    }

    Scaffold(
        topBar = { GameTopBar(onViewProfile = onViewProfile) },
        content = { contentPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(contentPadding)
                    .padding(12.dp),
                content = {
                    Spacer(modifier = modifier.height(12.dp))
                    Text(text = "Select Player Mode", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = modifier.height(12.dp))
                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .bounceClick(),
                        onClick = navToAdminScreen) {
                        Text(text = "ADMIN")
                    }
                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .bounceClick(),
                        onClick = navToPlayerScreen) {
                        Text(text = "PLAYER")
                    }
                    Button(
                        modifier = modifier
                            .fillMaxWidth()
                            .bounceClick(),
                        onClick = navToInstructions) {
                        Text(text = "GAME INSTRUCTIONS")
                    }
                }
            )
        }
    )

}