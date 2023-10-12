package com.teamnewton.treasurehunt.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.theme.PurpleGrey40
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginState
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignInUserData

@Composable
fun ProfileViewScreen(
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit,
) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.hand_wave_animation)
    )
    val firstName = Firebase.auth.currentUser?.displayName?.substringBefore(' ') ?:""


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

                    Text(
                        text = "Hello, $firstName",
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = modifier.height(16.dp))


                    Button(
                        modifier = modifier.fillMaxWidth(),
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