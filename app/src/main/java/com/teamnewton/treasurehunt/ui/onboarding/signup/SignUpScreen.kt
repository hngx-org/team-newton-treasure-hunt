package com.teamnewton.treasurehunt.ui.onboarding.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.theme.PurpleGrey40
import com.teamnewton.treasurehunt.ui.onboarding.login.LoginState


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    updateState: (LoginState) -> Unit,
    hasUser: Boolean,
    onNavigateToLogin: () -> Unit,
    signUp: () -> Unit,
) {

    val hasError = loginState.signUpError != null
    var showPassword by remember { mutableStateOf(value = false) }
    val keyBoard = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            Text(
                modifier = modifier.padding(12.dp),
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        content = { scaffoldPadding ->
            Column(
                modifier = modifier
                    .padding(scaffoldPadding)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {

                    if (hasError) {
                        Text(
                            text = loginState.signUpError ?: "SignUp Error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = loginState.userNameSignUp,
                        onValueChange = { updateState(loginState.copy(userNameSignUp = it)) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "")
                        },
                        label = {
                            Text(text = "Email")
                        },
                        isError = hasError
                    )

                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = loginState.passwordSignUp,
                        onValueChange = { updateState(loginState.copy(passwordSignUp = it)) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                        },
                        label = {
                            Text(text = "Password")
                        },
                        isError = hasError,
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            if (showPassword) {
                                IconButton(onClick = { showPassword = false }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility),
                                        contentDescription = "hide_password"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { showPassword = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility_off),
                                        contentDescription = "hide_password"
                                    )
                                }
                            }
                        }
                    )

                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = loginState.confirmPasswordSignUp,
                        onValueChange = { updateState(loginState.copy(confirmPasswordSignUp = it)) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                        },
                        label = {
                            Text(text = "Confirm Password")
                        },
                        isError = hasError,
                        visualTransformation = if (showPassword) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            if (showPassword) {
                                IconButton(onClick = { showPassword = false }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility),
                                        contentDescription = "hide_password"
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { showPassword = true }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_visibility_off),
                                        contentDescription = "hide_password"
                                    )
                                }
                            }
                        }
                    )

                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = {
                            keyBoard?.hide()
                            signUp()
                        },
                        content = {
                            Text(text = "Sign Up")
                        }
                    )

                    Spacer(modifier = modifier.height(10.dp))

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(text = "Already have an account?")
                            Spacer(modifier = modifier.width(5.dp))
                            TextButton(
                                onClick = onNavigateToLogin,
                                content = {
                                    Text(text = "Log In")
                                }
                            )
                        }
                    )

                    if (loginState.isLoading) {
                        CircularProgressIndicator()
                    }

                }
            )
        }
    )

   LaunchedEffect(
        key1 = hasUser,
        block = {
            if (hasUser) {
                onNavigateToLogin()
            }
        }
    )

}