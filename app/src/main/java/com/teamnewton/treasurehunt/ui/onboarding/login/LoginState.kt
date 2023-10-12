package com.teamnewton.treasurehunt.ui.onboarding.login

data class LoginState(
    val userNameLogin: String = "",
    val passwordLogin: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null,
    val userFirstName: String = "",
)
