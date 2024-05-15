package com.example.meddatabase.data.user

import com.example.meddatabase.data.DatabaseResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.ArrayList

class UserDAO(private val database: DatabaseReference) {
    suspend fun getAll() : Flow<DatabaseResult<List<User?>>> = callbackFlow {
        trySend(DatabaseResult.Loading)
        database.keepSynced(true)

        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(User::class.java)
                    user!!.uuid = childSnapshot.key.toString()
                    users.add(user)
                }
                trySend(DatabaseResult.Success(users))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(DatabaseResult.Error(Throwable(error.message)))
            }
        }
        database.addValueEventListener(event)
        awaitClose { close() }
    }
}