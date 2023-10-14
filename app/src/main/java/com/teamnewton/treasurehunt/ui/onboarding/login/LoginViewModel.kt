package com.teamnewton.treasurehunt.ui.onboarding.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamnewton.treasurehunt.app.repository.AuthRepository
import com.teamnewton.treasurehunt.ui.onboarding.signup.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val currentUser = repository.currentUser()

    val hasUser: Boolean = repository.hasUser()

    private val _loginUiState = MutableStateFlow(LoginState())
    val loginState = _loginUiState.asStateFlow()

    fun updateLoginState(state: LoginState) {
        _loginUiState.update { state }
    }

    private fun validateLoginForm(): Boolean {
     return   _loginUiState.value.userNameLogin.isNotBlank() && _loginUiState.value.passwordLogin.isNotBlank()
    }

    private fun validateSignUpForm(): Boolean {
      return  _loginUiState.value.userNameSignUp.isNotBlank() && _loginUiState.value.passwordSignUp.isNotBlank()
                && _loginUiState.value.confirmPasswordSignUp.isNotBlank()
    }

    fun createUser(context: Context) {
        viewModelScope.launch {
            try {
                if (!validateSignUpForm()) {
                    throw IllegalArgumentException("email and password cannot be empty")
                }
                _loginUiState.update { it.copy(isLoading = true) }
                if (_loginUiState.value.passwordSignUp != _loginUiState.value.confirmPasswordSignUp) {
                    throw IllegalArgumentException("passwords do not match")
                }
                _loginUiState.update { it.copy(signUpError = null) }
                repository.createUser(
                    email = _loginUiState.value.userNameSignUp,
                    password = _loginUiState.value.passwordSignUp,
                    onComplete = { success ->
                        if (success) {
                            Toast.makeText(context, "Successful Login", Toast.LENGTH_SHORT).show()
                            _loginUiState.update { it.copy(isSuccessLogin = true) }
                        } else {
                            Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show()
                            _loginUiState.update { it.copy(isSuccessLogin = false) }
                        }
                    }
                )

            }
            catch (e: Exception) {
                _loginUiState.update { it.copy(signUpError = e.localizedMessage) }
                e.printStackTrace()
            }
            finally {
                _loginUiState.update { it.copy(isLoading = false) }

            }
        }
    }

    fun loginUser(context: Context) {
        viewModelScope.launch {
            try {
                if (!validateLoginForm()) {
                    throw IllegalArgumentException("email and password cannot be empty")
                }
                _loginUiState.update { it.copy(isLoading = true) }
                _loginUiState.update { it.copy(loginError = null) }
                repository.login(
                    email = _loginUiState.value.userNameLogin,
                    password = _loginUiState.value.passwordLogin,
                    onComplete = { success ->
                        if (success) {
                            Toast.makeText(context, "Successful Login", Toast.LENGTH_SHORT).show()
                            _loginUiState.update { it.copy(isSuccessLogin = true, firstName = currentUser?.displayName ?:"") }
                        } else {
                            Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show()
                            _loginUiState.update { it.copy(isSuccessLogin = false) }
                        }
                    }
                )

            }
            catch (e: Exception) {
                _loginUiState.update { it.copy(loginError = e.localizedMessage) }
                e.printStackTrace()
            }
            finally {
                _loginUiState.update { it.copy(isLoading = false) }

            }
        }
    }

    fun resetState() {
        _loginUiState.update { LoginState() }
    }

    fun signOut() {
        repository.signOut()
    }

}