package com.teamnewton.treasurehunt.ui.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamnewton.treasurehunt.R
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
    LaunchedEffect(key1 = Unit) {
        if (!hasUser) {
            navToLoginPage.invoke()
        }
    }

    val crownComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.crown_animation)
    )

    val playerComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.player_animation)
    )

    Scaffold(
        topBar = { GameTopBar(onViewProfile = onViewProfile) },
        content = { contentPadding ->
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = modifier
                            .padding(contentPadding)
                            .padding(12.dp),
                        content = {
                            Spacer(modifier = modifier.height(12.dp))
                            Text(
                                text = "Select Player Mode",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            Spacer(modifier = modifier.height(12.dp))
                            Button(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .bounceClick(),
                                onClick = navToAdminScreen
                            ) {
                                Text(text = "ADMIN")
                                Spacer(modifier = modifier.width(5.dp))
                                Box(
                                    modifier = modifier.size(40.dp),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        LottieAnimation(
                                            modifier = modifier,
                                            composition = crownComposition,
                                            iterations = LottieConstants.IterateForever,
                                        )
                                    }
                                )
                            }
                            Button(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .bounceClick(),
                                onClick = navToPlayerScreen
                            ) {
                                Text(text = "PLAYER")
                                Spacer(modifier = modifier.width(5.dp))
                                Box(
                                    modifier = modifier.size(40.dp),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        LottieAnimation(
                                            modifier = modifier,
                                            composition = playerComposition,
                                            iterations = LottieConstants.IterateForever,
                                        )
                                    }
                                )
                            }
                            Button(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .bounceClick(),
                                onClick = navToInstructions
                            ) {
                                Text(text = "GAME INSTRUCTIONS")
                            }
                        }
                    )
                }
            )
        }
    )
}