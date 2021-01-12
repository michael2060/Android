package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Addsendinfo::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userdao(): UserDao
    abstract fun addsendinfoDao(): AddsendinfoDao
}