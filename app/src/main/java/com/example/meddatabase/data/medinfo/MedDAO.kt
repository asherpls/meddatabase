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

class MedDAO(private val database: DatabaseReference) {
    suspend fun getMedInfo(userUUID: String) : Flow<DatabaseResult<List<MedInfo?>>> = callbackFlow {
        trySend(DatabaseResult.Loading)
        database.child(userUUID).keepSynced(true)

        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val medInfos = ArrayList<MedInfo>()
                for (childSnapshot in snapshot.children) {
                    val medicine = childSnapshot.getValue(MedInfo::class.java)
                    medicine!!.id = childSnapshot.key.toString()
                    medInfos.add(medicine)
                }
                trySend(DatabaseResult.Success(medInfos))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(DatabaseResult.Error(Throwable(error.message)))
            }
        }
        database.child(userUUID).addValueEventListener(event)
        awaitClose { close() }
    }

    suspend fun getEntireDatabase() : Flow<DatabaseResult<List<MedInfo?>>> = callbackFlow {
        trySend(DatabaseResult.Loading)
        database.keepSynced(true)

        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val medInfos = ArrayList<MedInfo>()
                for (childSnapshot in snapshot.children) {
                    val medicine = childSnapshot.getValue(MedInfo::class.java)
                    medicine!!.id = childSnapshot.key.toString()
                    medInfos.add(medicine)
                }
                trySend(DatabaseResult.Success(medInfos))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(DatabaseResult.Error(Throwable(error.message)))
            }
        }
        database.child("all_user").addValueEventListener(event)
        awaitClose { close() }
    }


    fun insert(newMedInfo: MedInfo, userAuthUUID: String){
        var MedID = UUID.randomUUID().toString()
        database.child(userAuthUUID).child(MedID).setValue(newMedInfo)
        database.child("all_user").child(MedID).setValue(newMedInfo)

    }

    fun update(editMed: MedInfo, userAuthUUID: String) {
        val medId = editMed.id.toString()
        database.child(userAuthUUID).child(medId).setValue(editMed)
        database.child("all_user").child(medId).setValue(editMed)

    }

    fun delete(medI: MedInfo, userAuthUUID: String) {
        database.child(userAuthUUID).child(medI.id.toString()).removeValue()
        database.child("all_user").child(medI.id.toString()).removeValue()
    }
}