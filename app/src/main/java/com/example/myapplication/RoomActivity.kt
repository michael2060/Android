package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.*
import timber.log.Timber

class RoomActivity : AppCompatActivity() {
    private val job = Job()
    private val coroutinScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        db = Room.databaseBuilder(this, UserDatabase::class.java, "SAMPLE_DB").build()

        btnRoomAdd.setOnClickListener {
            coroutinScope.launch {
                withContext(Dispatchers.IO) {
                    val user = User(0, "michael")
                    db.userdao().insert(user)
                }
            }
        }
        btnRoomLoad.setOnClickListener {
            coroutinScope.launch {
                withContext(Dispatchers.IO) {
                    val users = db.userdao().getAll()
                    users.forEach {
                        Timber.d("user = ${it.uid}/ ${it.toString()}//")
                    }
                }
            }
        }
    }
}