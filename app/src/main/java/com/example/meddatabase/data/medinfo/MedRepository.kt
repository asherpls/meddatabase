package com.example.meddatabase.data.medinfo

import com.example.meddatabase.data.DatabaseResult
import com.example.meddatabase.data.interfaces.Repo
import com.example.meddatabase.data.interfaces.UpdateMedListener
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

class MedRepository(private val dao: MedDAO) : Repo<MedInfo>, UpdateMedListener {
    override fun delete(entry: MedInfo) = dao.delete(entry)

    override fun add(entry: MedInfo)= dao.insert(entry)

    override fun edit(entry: MedInfo) = dao.update(entry)

    override suspend fun getAll(): Flow<DatabaseResult<List<MedInfo?>>> {
        return dao.getMedInfo()
    }

    override fun updateUserListener(contactToListenTo: DatabaseReference) {
        dao.updateMedListener(contactToListenTo)
    }
}