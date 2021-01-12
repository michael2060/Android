package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Addsendinfo(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name = "name") val name: String?,

    @ColumnInfo(name = "send_time") val sendTime: String?,

    @ColumnInfo(name = "send_message") val sendMessage: String?,

    @ColumnInfo(name = "send_tool") val sendTool: Int?,

    @ColumnInfo(name = "send_status") val sendStatus: Int?

)