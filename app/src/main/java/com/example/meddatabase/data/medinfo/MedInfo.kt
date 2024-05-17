package com.example.meddatabase.data.medinfo

import java.util.Date

data class MedInfo(
    var medName: String? =null,
    var details: String? =null,
    var formattedDate: String? =null,
) {
    var id:String? =null //UUID
    override fun toString(): String = "$medName [Exp: $formattedDate]"
    fun toFatString(): String = "$medName $details [Exp: $formattedDate]"
}