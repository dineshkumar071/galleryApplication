package com.example.gridview

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class galleryinsideAdapter(var c:Context,val singlephotolist:ArrayList<singlephoto>):RecyclerView.Adapter<galleryinsideAdapter.ViewHolder1>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
       val v=LayoutInflater.from(parent.context).inflate(R.layout.singlephoto,parent,false)
        return ViewHolder1(v)
    }

    override fun getItemCount(): Int {
        return singlephotolist.size
    }

    override fun onBindViewHolder(holder: galleryinsideAdapter.ViewHolder1, position: Int) {
        var singlepht:singlephoto=singlephotolist[position]
        holder.imgtxtview.text=singlepht.title
        holder.imgview.setImageURI(singlepht.thumbnails)
//        val bitmap =
//            MediaStore.Images.Media.getBitmap(c.contentResolver, singlepht.thumbnails.t)
//        holder.imgview.setImageBitmap(bitmap)
        holder.imgcardview.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var intent= Intent(c,thirdActivity::class.java)
                val bundle= Bundle()
                bundle.putString("path", singlepht.thumbnails.toString())
                intent.putExtras(bundle)
                c.startActivity(intent)
            }
        })

    }

    class ViewHolder1(itemView: View):RecyclerView.ViewHolder(itemView){
        var imgtxtview=itemView.findViewById(R.id.phototxtview)as TextView
        var imgview=itemView.findViewById(R.id.imgview1)as ImageView
        var imgcardview=itemView.findViewById(R.id.scardView)as CardView

    }
}