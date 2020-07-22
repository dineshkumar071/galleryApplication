package com.example.gridview

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class gridviewAdapter(
    var c: Context,
    val photolist: MutableList<Photos>,
    var itemClickListerner1: itemClickListerner
) : RecyclerView.Adapter<gridviewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagetextview = itemView.findViewById(R.id.textname1) as TextView
        var imageimgview = itemView.findViewById(R.id.imageView) as ImageView
        var imagesizeview = itemView.findViewById(R.id.textname2) as TextView
        var imagecardView = itemView.findViewById(R.id.cardView_id) as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.galleryview, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return photolist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photolist1: Photos = photolist[position]
        holder.imagetextview.text = photolist1.title
        holder.imagesizeview.text = (photolist1.imageCount).toString()
        photolist1.lastImagePath.let { holder.imageimgview.setImageURI(it) }
        holder.imagecardView.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                itemClickListerner1.itemClick(photolist1.title,photolist1.lastImagePath)

            }
        })
    }

    interface itemClickListerner {
        fun itemClick(title: String?,lastimagepath: Uri?)
    }
}