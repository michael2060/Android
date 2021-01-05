package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.Intent.CATEGORY_APP_GALLERY
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hw_intent.*

class HwIntentActivity : AppCompatActivity() {
    companion object{
        const val camera_result = 101
        const val select_result = 202
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw_intent)
        btnLineAct.setOnClickListener{
            openline()
        }
        btnEmailsend.setOnClickListener {
            sendTempEmail()
        }
        btncamera.setOnClickListener {
            photocapture()
        }
        btnGallery.setOnClickListener {
            photoselect()
        }
    }
    private fun openline(){
        val lineurl = Uri.parse("https://line.me/R/")
        val intent = Intent(Intent.ACTION_VIEW,lineurl)
        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }
    }

    private fun sendTempEmail(){
        val intent = Intent(Intent.ACTION_SENDTO).apply{
            type = "text/plain"
            data = Uri.parse("mailto:")
            val address = etxtemailaddress.text?.toString()
            val title = etxtemailtitle.text?.toString()
            val content = etxtemailcontent.text?.toString()
            var addresslist = arrayOf(address)
            

            putExtra(Intent.EXTRA_EMAIL, addresslist)
            putExtra(Intent.EXTRA_SUBJECT,title)
            putExtra(Intent.EXTRA_TEXT,content)
        }
        startActivity(intent)
    }

    private fun photocapture(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, camera_result)
    }

    private fun photoselect(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI).apply{
            type = "image/*"
        }
        startActivityForResult(intent,select_result)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK) {
            when (requestCode) {
                camera_result -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    imgphotocap.setImageBitmap(bitmap)
                }
                select_result->{
                    val bitmap:Uri? = data?.data
                    imgphotocap.setImageURI(bitmap)
                }
            }
        }



    }

}