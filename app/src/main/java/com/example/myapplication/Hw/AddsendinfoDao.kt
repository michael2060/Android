package com.example.myapplication.Hw

import androidx.room.*

@Dao
interface AddsendinfoDao {
    @Query("SELECT * FROM addsendinfo")
    fun getAll(): List<Addsendinfo>

    @Insert
    fun insert(addSendInfo: Addsendinfo)

    @Update
    fun update(addSendInfo: Addsendinfo)

    @Transaction
    @Query("DELETE FROM addsendinfo WHERE uid =(:msgId)")
    fun deleteMsg(msgId: Int)


    @Transaction
    @Query("UPDATE addsendinfo SET send_message =(:msg),send_time = (:time)  WHERE uid =(:msgId)")
    fun updMsg(msgId: Int, msg: String, time: String)


}