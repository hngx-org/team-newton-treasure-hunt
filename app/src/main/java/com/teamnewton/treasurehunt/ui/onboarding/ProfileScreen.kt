package com.teamnewton.treasurehunt.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.theme.PurpleGrey40
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignInUserData

@Composable
fun ProfileViewScreen(
    modifier: Modifier = Modifier,
    userData: SignInUserData,
    onSignOut: () -> Unit,
) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.hand_wave_animation)
    )

    Scaffold(
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .padding(scaffoldPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Box(
                        modifier = modifier.size(200.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            LottieAnimation(
                                modifier = modifier,
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                            )
                        }
                    )

                    if (userData.username != null) {
                        Text(
                            text = userData.username.substringBefore(' '),
                            textAlign = TextAlign.Center,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = modifier.height(16.dp))
                    }

                    Button(
                        shape = CutCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleGrey40),
                        onClick = onSignOut,
                        content = {
                            Text(text = stringResource(id = R.string.sign_out))
                        }
                    )
                }
            )
        }
    )
}