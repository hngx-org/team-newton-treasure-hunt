package com.teamnewton.treasurehunt.ui.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
) {
    LaunchedEffect(key1 = hasUser){
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
                    Text(text = "Select Player Mode", style = MaterialTheme.typography.headlineMedium)
                    Button(
                        modifier = modifier.fillMaxWidth().bounceClick(),
                        onClick = { /*TODO*/ }) {
                        Text(text = "ADMIN")
                    }
                    Button(
                        modifier = modifier.fillMaxWidth().bounceClick(),
                        onClick = { /*TODO*/ }) {
                        Text(text = "PLAYER")
                    }
                }
            )
        }
    )

}