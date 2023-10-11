package com.teamnewton.treasurehunt.ui.onboarding.signup

data class SignInResult(
    val data: SignInUserData?,
    val errorMessage: String?
)


data class SignInUserData(
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?
)