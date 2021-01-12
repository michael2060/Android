package com.example.myapplication

import androidx.room.*

@Dao
interface AddsendinfoDao {
    @Query("SELECT * FROM addsendinfo")
    fun getAll(): List<Addsendinfo>

    @Insert
    fun insert(addSendInfo: Addsendinfo)

    @Transaction
    @Query("DELETE FROM addsendinfo WHERE uid =(:msgId)")
    fun deleteMsg( msgId: Int)



}