package com.example.meddatabase.core

import android.content.Context
import com.example.meddatabase.data.auth.AuthRepo
import com.example.meddatabase.data.auth.AuthRepository
import com.example.meddatabase.data.medinfo.MedDAO
import com.example.meddatabase.data.medinfo.MedRepo
import com.example.meddatabase.data.medinfo.MedRepository
import com.example.meddatabase.data.user.UserDAO
import com.example.meddatabase.data.user.UserRepo
import com.example.meddatabase.data.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val CONTACT_ROOT_FOLDER = "med-database"
private const val USERS_ROOT_FOLDER = "users"
private const val DATABASE_URL ="https://med-database-e58c4-default-rtdb.europe-west1.firebasedatabase.app/"

interface AppContainer {
    val medRepository: MedRepo
    val userRepository: UserRepo
    val authRepository: AuthRepo
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val medRepository: MedRepo
    override val userRepository: UserRepo
    override var authRepository: AuthRepo  = AuthRepository(FirebaseAuth.getInstance())

    init {
        val medRoot = FirebaseDatabase.getInstance(DATABASE_URL).getReference(CONTACT_ROOT_FOLDER)
        val medDAO = MedDAO(medRoot)
        medRepository = MedRepository(medDAO)

        val userRoot = FirebaseDatabase.getInstance(DATABASE_URL).getReference(USERS_ROOT_FOLDER)
        val userDAO = UserDAO(userRoot)
        userRepository = UserRepository(userDAO)

        authRepository = AuthRepository(FirebaseAuth.getInstance())
    }

    fun returnContextForDatabaseListener(contact: FirebaseUser?): DatabaseReference{
        if (contact != null) {
            return  FirebaseDatabase.getInstance(DATABASE_URL).getReference(CONTACT_ROOT_FOLDER).child(/* pathString = */
                contact.uid)
        };
        return TODO("Provide the return value")
    }
}