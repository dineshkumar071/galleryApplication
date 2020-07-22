package com.example.gridview

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_second.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("PLUGIN_WARNING")
class  secondActivity : AppCompatActivity() {

    var bmp:Bitmap?=null
    var phlist=ArrayList<singlephoto>()
    var path:String?=null
    var intent1:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val bundle = intent.extras
        intent1= bundle?.getString("title")
         path = bundle?.getString("path")

        //Log.d("title",intent1)
        this.title = intent1
        singlerecyclerview.layoutManager= GridLayoutManager(this,3)
        floatingActionButton2.setOnClickListener {
            //check runtime permission
            pickImageGallery()

        }
        singlerecyclerview.adapter=galleryinsideAdapter(this,phlist)
        backup()
    }
    private fun pickImageGallery() {
        // Intent the gallery
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_PICK_CODE)

    }
    companion object {
        //image pick code
        private val IMG_PICK_CODE: Int = 1000
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMG_PICK_CODE) {
            //val image = data?.extras?.get("data")
            data?.let { it1 ->
                it1.data?.let { it2 ->
                        val inputStream: InputStream? = contentResolver.openInputStream(it2)
                        bmp = BitmapFactory.decodeStream(inputStream)
                    val fileName: String = SimpleDateFormat("yyyyMMddHHmm",Locale.getDefault()).format(Date())
                        val file = File(path, "$fileName.jpg")

                        try {
                            val stream: OutputStream?
                            stream = FileOutputStream(file)
                            bmp?.compress(Bitmap.CompressFormat.JPEG, 100, stream)

                        } catch (e: IOException) // Catch the exception
                        {
                            e.printStackTrace()
                        }
                        val savedImageURI: Uri = Uri.parse(file.absolutePath)
                        //bmp.setImageURI(savedImageURI)
                        // bmp.setText("Image saved in external storage.\n$savedImageURI")
                        phlist.add(singlephoto("image", savedImageURI))
                        singlerecyclerview.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    private fun backup()
    {
        singlerecyclerview.adapter?.notifyDataSetChanged()
        val file = File(Environment.getExternalStorageDirectory(), "GALLERY/"+title)

        if (!file.exists()) {
            file.mkdirs()
            path = file.absolutePath
        } else {
            path = File(Environment.getExternalStorageDirectory(), "GALLERY/"+title).absolutePath
        }
        val files: Array<File> = file.listFiles()
        for(i in files) {
            phlist.add(singlephoto(i.name, i.toUri()))
        }
        singlerecyclerview.adapter?.notifyDataSetChanged()
    }
    /*override fun onResume() {
        super.onResume()
        phlist.clear()
        singlerecyclerview.adapter?.notifyDataSetChanged()
        val file = File(Environment.getExternalStorageDirectory(), "GALLERY/"+title)

        if (!file.exists()) {
            file.mkdirs()
            path = file.absolutePath
        } else {
            path = File(Environment.getExternalStorageDirectory(), "GALLERY/"+title).absolutePath
        }
        val files: Array<File> = file.listFiles()
        for(i in files) {
            phlist.add(singlephoto(i.name, i.toUri()))
        }
        singlerecyclerview.adapter?.notifyDataSetChanged()
    }*/

    }

