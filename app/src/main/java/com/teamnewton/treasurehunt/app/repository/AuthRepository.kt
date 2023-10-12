package com.teamnewton.treasurehunt.app.repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean {
        Log.i("AUTH REPO", "${Firebase.auth.currentUser != null}")
        Log.i("AUTH REPO", "${Firebase.auth.currentUser}")
        return Firebase.auth.currentUser != null
    }

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit,
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }.await()
    }

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit,
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }.await()
    }

    fun signOut() = Firebase.auth.signOut()

}