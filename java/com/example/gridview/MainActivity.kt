package com.example.gridview

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.DialogTitle
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File



class MainActivity : AppCompatActivity(), MyFragement.OnCompleteListener {
    val photolist = mutableListOf<Photos>()
    val users = ArrayList<String>()
    var mainPath: String? = null
    var txt: String? = null
    var sidePath:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Album ")
        RecyclerView.layoutManager = GridLayoutManager(this, 2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //Permission denied
                val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                //permission already granted.
                createMainFolder()
            }
        } else {
            //system os less than marshmellow
            createMainFolder()
        }

        floatingActionButton.setOnClickListener {
            val fm = supportFragmentManager
            val myFragement = MyFragement()
            myFragement.show(fm, "simple fragement")
        }
        RecyclerView.adapter = gridviewAdapter(this, photolist,object :gridviewAdapter.itemClickListerner{
            override fun itemClick(title: String?,lastpath:Uri?) {
                var intent= Intent(this@MainActivity,secondActivity::class.java)
                val bundle=Bundle()
                bundle.putString("title",title)
                bundle.putString("path",lastpath.toString())
                intent.putExtras(bundle)
                this@MainActivity.startActivity(intent)
            }
        })
    }

    companion object {
        //permission code
        private val PERMISSION_CODE: Int = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from the popup granted
                    // pickImageGallery();
                    createMainFolder()
                } else {
                    // permission from the popup denied
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createMainFolder() {
        val file = File(Environment.getExternalStorageDirectory(), "GALLERY")
        if (!file.exists()) {
            file.mkdirs()
            mainPath = file.absolutePath
        } else {
            mainPath = File(Environment.getExternalStorageDirectory(), "GALLERY").absolutePath
        }
        backup1()
    }

    override fun onComplete(time: String?) {
        txt = time
        val fileM = File("$mainPath/$txt")
        if (!fileM.exists()) {
            fileM.mkdir()
        }
        sidePath = fileM.absolutePath
        photolist.add(Photos(txt, R.drawable.index, fileM.absolutePath,sidePath?.toUri()))
        //Log.d("tag1",txt)
        RecyclerView.adapter?.notifyDataSetChanged()
    }
    private fun backup1()
    {
        RecyclerView.adapter?.notifyDataSetChanged()
        val file = File(Environment.getExternalStorageDirectory(), "GALLERY")
        if (!file.exists()) {
            file.mkdirs()
            mainPath = file.absolutePath
        } else {
            mainPath = File(Environment.getExternalStorageDirectory(), "GALLERY").absolutePath
        }
        val files: Array<File> = file.listFiles()
        for (i in files)
        {
            val file = File(Environment.getExternalStorageDirectory(), "GALLERY/"+i.name)
            if (file.exists()) {
                val file1 = file.listFiles()

                if (file1.size > 0) {
                    photolist.add(Photos(i.name, file1.size,file.absolutePath, file1.get(file1.size - 1).toUri()))
                }
                else
                {
                    photolist.add(Photos(txt, R.drawable.index, sidePath,sidePath?.toUri()))
                }
            }
        }
        RecyclerView.adapter?.notifyDataSetChanged()
    }

    /*override fun onResume() {
        super.onResume()
        photolist.clear()
        RecyclerView.adapter?.notifyDataSetChanged()
        val file = File(Environment.getExternalStorageDirectory(), "GALLERY")
        if (!file.exists()) {
            file.mkdirs()
            mainPath = file.absolutePath
        } else {
            mainPath = File(Environment.getExternalStorageDirectory(), "GALLERY").absolutePath
        }
        val files: Array<File> = file.listFiles()
        for (i in files)
        {
            val file = File(Environment.getExternalStorageDirectory(), "GALLERY/"+i.name)
            if (file.exists()) {
                val file1 = file.listFiles()

                if (file1.size > 0) {
                    photolist.add(Photos(i.name, file1.size, file1.get(file1.size - 1).toUri()))
                }
            }
        }
        RecyclerView.adapter?.notifyDataSetChanged()
    }*/
}


