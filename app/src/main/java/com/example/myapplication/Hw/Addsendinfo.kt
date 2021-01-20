package com.example.myapplication.Hw

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
class Addsendinfo(
        @PrimaryKey(autoGenerate = true) val uid: Int,

        @ColumnInfo(name = "name") val name: String?,

        @ColumnInfo(name = "send_time") var sendTime: String?,

        @ColumnInfo(name = "send_message") var sendMessage: String?,

        @ColumnInfo(name = "send_tool") val sendTool: Int?,

        @ColumnInfo(name = "send_status") val sendStatus: Int?

) : Parcelable