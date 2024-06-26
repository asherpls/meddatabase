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
import com.google.firebase.database.FirebaseDatabase

private const val CONTACT_ROOT_FOLDER = "med-database"
private const val USERS_ROOT_FOLDER = "users"
private const val DATABASE_URL ="https://med-database-e58c4-default-rtdb.europe-west1.firebasedatabase.app/"

interface AppContainer {
    val medRepository: MedRepo
    val userRepository: UserRepo
    val authRepository: AuthRepo

    val isRunningTest: Boolean
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val isRunningTest : Boolean by lazy {
        try {
            Class.forName("androidx.test.espresso.Espresso")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

        override val medRepository: MedRepo
    override val userRepository: UserRepo
    override var authRepository: AuthRepo  = AuthRepository(FirebaseAuth.getInstance())

    init {
        val APPENDED_TEST_PATH = if (isRunningTest) "test" else String()

        val medRoot = FirebaseDatabase.getInstance(DATABASE_URL).getReference("$APPENDED_TEST_PATH$CONTACT_ROOT_FOLDER")
        val medDAO = MedDAO(medRoot)
        medRepository = MedRepository(medDAO)

        val userRoot = FirebaseDatabase.getInstance(DATABASE_URL).getReference("$APPENDED_TEST_PATH$USERS_ROOT_FOLDER")
        val userDAO = UserDAO(userRoot)
        userRepository = UserRepository(userDAO)

        authRepository = AuthRepository(FirebaseAuth.getInstance())
    }
}