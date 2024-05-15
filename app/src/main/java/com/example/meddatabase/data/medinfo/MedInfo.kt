package com.example.meddatabase.data.medinfo

import java.util.Date

data class MedInfo(
    var medName: String? =null,
    var details: String? =null,
    var startDate: Int? = null,
    var endDate: Int? = null,
) {
    var id:String? =null //UUID
    override fun toString(): String = "$medName $details ${startDate.toString()}"
}