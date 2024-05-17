package com.example.meddatabase.data.user

import com.example.meddatabase.data.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface UserRepo{
    suspend fun getAll(): Flow<DatabaseResult<List<User?>>>
}

class UserRepository(private val dao: UserDAO) : UserRepo {
    override suspend fun getAll(): Flow<DatabaseResult<List<User?>>> {
        return dao.getAll()
    }
}