package com.teamnewton.treasurehunt.ui.onboarding.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.theme.PurpleGrey40


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    state: SignInState,
    onSignInClick: () -> Unit,
) {

    val context = LocalContext.current
    LaunchedEffect(
        key1 = state.isSignInSuccessful,
        block = {
            state.signInErrorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    )

    Scaffold(
        content = { scaffoldPadding ->

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(scaffoldPadding)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
                content = {
                    Button(
                        shape = CutCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleGrey40),
                        onClick = onSignInClick,
                        content = {
                            Text(text = stringResource(id = R.string.sign_in))
                        }
                    )
                }
            )
        }
    )

}