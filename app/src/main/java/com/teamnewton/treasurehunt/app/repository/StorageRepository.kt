package com.teamnewton.treasurehunt.app.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.teamnewton.treasurehunt.app.model.Game
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


const val TREASURES_COLLECTION_REF = "treasurehunts"

class StorageRepository() {

    fun user() = Firebase.auth.currentUser

   // fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = user()?.uid.orEmpty()

    private val treasurehuntsRef: CollectionReference =
        Firebase.firestore.collection(TREASURES_COLLECTION_REF)


    //get all treasureHunts
    fun getTreasuresHunts(userId: String): Flow<NetworkResults<List<Game>>> = callbackFlow {
        var snapShotListener: ListenerRegistration? = null
        Log.i("STORE","getreasurehunts")
        Log.i("STORE",treasurehuntsRef.id)

        try {
            snapShotListener = treasurehuntsRef
                //.orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapShot, error ->
                    val response = if (snapShot != null) {
                        val treasurehunts = snapShot.toObjects(Game::class.java)
                        NetworkResults.Success(data = treasurehunts)
                    } else {
                        NetworkResults.Error(throwable = error?.cause)
                    }

                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(NetworkResults.Error(throwable = e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapShotListener?.remove()
        }
    }

    //get One Treasure Hunt
    fun getTreasureHunt(
        treasurehuntId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Game?) -> Unit
    ) {
        treasurehuntsRef
            .document(treasurehuntId)
            .get()
            .addOnSuccessListener { gameSnapshot ->
                onSuccess(gameSnapshot.toObject(Game::class.java))
            }
            .addOnFailureListener { exception ->
                onError(exception.cause)

            }
    }


    fun addTreasureHunt(
        userId: String,
        treasureHuntName: String,
        treasureLocationLat: Double,
        treasureLocationLong: Double,
        treasureClue: String,
        treasureReward: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit,
    ) {
        Log.i("STORE REPO", "add called")

        val documentId = treasurehuntsRef.document().id
        val newGame = Game(
            userId = userId,
            timestamp = timestamp,
            documentId = documentId,
            treasureHuntName = treasureHuntName,
            treasureLocationLat = treasureLocationLat,
            treasureLocationLong = treasureLocationLong,
            treasureClue = treasureClue,
            treasureReward = treasureReward
        )

        treasurehuntsRef
            .document(documentId)
            .set(newGame)
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful)

            }

    }

    //when a hunt is complete it will be deleted, alternatively, admin can delete
    fun deleteTreasure(treasurehuntId: String, onComplete: (Boolean) -> Unit) {
        treasurehuntsRef
            .document(treasurehuntId)
            .delete()
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful)
            }
    }

    fun updateTreasure(
        treasurehuntId: String,
        treasureHuntName: String,
        treasureLocationLat: Double,
        treasureLocationLong: Double,
        treasureClue: String,
        treasureReward: String,
        onResult: (Boolean) -> Unit,
    ) {
        val updateTreasureData = hashMapOf<String, Any>(
            "name" to treasureHuntName,
            "location_lat" to treasureLocationLat,
            "location_long" to treasureLocationLong,
            "clue" to treasureClue,
            "reward" to treasureReward
        )
        treasurehuntsRef
            .document(treasurehuntId)
            .update(updateTreasureData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }


    }


}