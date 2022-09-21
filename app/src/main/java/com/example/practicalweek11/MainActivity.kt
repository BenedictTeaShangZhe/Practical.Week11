package com.example.practicalweek11

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class MainActivity : AppCompatActivity(),itemAdapter.OnItemClickListener {
    private lateinit var image:ImageView
    private lateinit var storageRef:StorageReference
    private  lateinit var imgUri:Uri

    private val itemList = listOf(
        item("Pen", 2.50,),
        item("Pencil", 3.50),
        item("Color Pen", 4.50),
        item("Eraser", 5.50),
        item("Correction Tape", 6.50),
        item("Ruler", 7.50),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.image)
        val btnBrowse:Button = findViewById(R.id.btnBrowse)
        val btnUpload:Button = findViewById(R.id.btnUpload)
        val btnGet:Button = findViewById(R.id.btnGet)

        storageRef = FirebaseStorage.getInstance().reference


        val recycleView:RecyclerView = findViewById(R.id.recycleView)

        val itemAdapter = itemAdapter(itemList,this)

        recycleView.adapter = itemAdapter

        recycleView.layoutManager= LinearLayoutManager(this)
        //recycleView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //recycleView.layoutManager= GridLayoutManager(this,2)

        recycleView.setHasFixedSize(true)

        btnBrowse.setOnClickListener(){
            getPhoto.launch("image/*")
        }

        btnUpload.setOnClickListener(){
            val myPhoto = storageRef.child("photo/a.png")
            myPhoto.putFile(imgUri).addOnSuccessListener {Toast.makeText(applicationContext,"Success",Toast.LENGTH_LONG).show()  }.
            addOnFailureListener{Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show() }
        }

        btnGet.setOnClickListener(){
            val myPhoto = storageRef.child("photo/a.png")
            val file = File.createTempFile("temp","png")
            myPhoto.getFile(file).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                image.setImageBitmap(bitmap)}.
            addOnFailureListener{Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show() }
        }

    }


    private val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()){Uri->
        imgUri = Uri!!//Means must not null
        image.setImageURI(Uri)
    }

    override fun itemClick(position: Int) {
        val selectItem = itemList[position]
        Toast.makeText(this, selectItem.productDescription, Toast.LENGTH_SHORT).show()
    }

}