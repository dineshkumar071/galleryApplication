package com.example.gridview

import android.graphics.Bitmap
import android.net.Uri

class singlephoto{
    var title:String?=""
    var thumbnails:Uri
    //var sidepath:String?=null
    constructor(title: String?, thumbnails: Uri) {
        this.title = title
        this.thumbnails = thumbnails
    }
}