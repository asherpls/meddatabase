package com.example.meddatabase.data.interfaces

import com.google.firebase.database.DatabaseReference

interface UpdateMedListener {
    fun updateUserListener(contactToListenTo: DatabaseReference)
}