package com.teamnewton.treasurehunt.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamnewton.treasurehunt.R
import kotlinx.coroutines.delay

private const val SPLASH_DURATION = 3000

@Composable
fun SplashScreen(
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.treasure_chest)
    )

    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {

                    LottieAnimation(
                        modifier = modifier,
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                    )
                }
            )
            LaunchedEffect(
                key1 = true,
                block = {
                    delay(SPLASH_DURATION.toLong())
                    onNext()
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SplashScreen(onNext = { })

}