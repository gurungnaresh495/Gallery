package com.example.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_layout_images.view.*

class ImagesRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<ImagesRecyclerViewAdapter.ImagesViewHolder>(){

    private var imagePathList = ArrayList<String>()
    inner class ImagesViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(path:String)
        {
            Glide.with(context).load(path).into(view.row_layout_image_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_images, parent, false))
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(imagePathList[position])
    }

    override fun getItemCount(): Int {
        return this.imagePathList.size
    }

    fun updateList(list: ArrayList<String>)
    {
        this.imagePathList = list
        notifyDataSetChanged()
    }
}