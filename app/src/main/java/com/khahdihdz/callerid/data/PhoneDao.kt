package com.khahdihdz.callerid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhoneDao {
    @Query("SELECT * FROM phone_numbers WHERE number LIKE '%' || :query || '%' OR displayName LIKE '%' || :query || '%' ORDER BY spamScore DESC LIMIT 100")
    suspend fun search(query: String): List<PhoneNumberEntity>

    @Query("SELECT * FROM phone_numbers WHERE number = :number LIMIT 1")
    suspend fun find(number: String): PhoneNumberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PhoneNumberEntity)
}
