package com.khahdihdz.callerid.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "phone_numbers", indices = [Index(value = ["number"], unique = true)])
data class PhoneNumberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: String,
    val displayName: String? = null,
    val category: String = "UNKNOWN",
    val spamScore: Int = 0,
    val source: String = "LOCAL",
    val updatedAt: Long = System.currentTimeMillis()
)
