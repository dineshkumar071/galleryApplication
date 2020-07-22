package com.example.gridview

import android.net.Uri

class Photos{
    var title:String?=""
    var imageCount:Int=0
    var pathname:String?=null
    var lastImagePath:Uri?=null

    constructor(title: String?,  thumbnails: Int,pathname:String?,sidepath:Uri?) {
        this.title = title
        this.imageCount = thumbnails
        this.pathname=pathname
        this.lastImagePath=sidepath
    }
}