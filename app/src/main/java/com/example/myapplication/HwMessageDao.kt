package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AddsendinfoDao {
    @Query("SELECT * FROM addsendinfo")
    fun getAll(): List<Addsendinfo>

    @Insert
    fun insert(addSendInfo: Addsendinfo)
}