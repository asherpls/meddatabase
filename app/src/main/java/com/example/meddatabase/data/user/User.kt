package com.example.meddatabase.data.user

data class User(
    var firstName: String? =null,
    var surname: String? =null
) {
    var uuid:String? =null
    override fun toString(): String = "$firstName $surname"
}