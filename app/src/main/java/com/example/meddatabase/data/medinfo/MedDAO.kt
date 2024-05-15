package com.example.meddatabase.data.medinfo

import com.example.meddatabase.data.DatabaseResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

class MedDAO() {
    private var event: ValueEventListener?=null;
    private lateinit var medDatabaseRoot :DatabaseReference

    fun updateMedListener(contactToListenTo: DatabaseReference){
        this.medDatabaseRoot = contactToListenTo
    }

    suspend fun getMedInfo() : Flow<DatabaseResult<List<MedInfo?>>> = callbackFlow {
        trySend(DatabaseResult.Loading)
        medDatabaseRoot.keepSynced(true)

        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val medInformation = ArrayList<MedInfo>()
                for (childSnapshot in snapshot.children) {
                    val medInfo = childSnapshot.getValue(MedInfo::class.java)
                    medInfo!!.id = childSnapshot.key.toString()
                    medInformation.add(medInfo)
                }
                trySend(DatabaseResult.Success(medInformation))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(DatabaseResult.Error(Throwable(error.message)))
            }
        }
        medDatabaseRoot.addValueEventListener(event)
        awaitClose { close() }
    }

    fun insert(newMedInfo: MedInfo) = medDatabaseRoot.child(UUID.randomUUID().toString()).setValue(newMedInfo)

    fun update(editMedInfo: MedInfo) = medDatabaseRoot.child(editMedInfo.id.toString()).setValue(editMedInfo)

    fun delete(medInfo: MedInfo) = medDatabaseRoot.child(medInfo.id.toString()).removeValue()
}