package com.example.meddatabase.data.medinfo

import com.example.meddatabase.data.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface MedRepo{
    fun delete(entry: MedInfo, userUUID: String)

    fun add(entry: MedInfo, userUUID: String)

    fun edit(entry: MedInfo, userUUID: String)

    suspend fun getAll(userUUID: String): Flow<DatabaseResult<List<MedInfo?>>>

    suspend fun getEntirety(): Flow<DatabaseResult<List<MedInfo?>>>
}

class MedRepository(private val dao: MedDAO) : MedRepo {
    override fun delete(entry: MedInfo, userUUID: String) {dao.delete(entry, userUUID)}

    override fun add(entry: MedInfo, userUUID: String){ dao.insert(entry, userUUID)}

    override fun edit(entry: MedInfo, userUUID: String) { dao.update(entry, userUUID)}

    override suspend fun getAll(userUUID: String): Flow<DatabaseResult<List<MedInfo?>>> {
        return dao.getMedInfo(userUUID)
    }
    override suspend fun getEntirety(): Flow<DatabaseResult<List<MedInfo?>>> {
        return dao.getEntireDatabase()
    }


}