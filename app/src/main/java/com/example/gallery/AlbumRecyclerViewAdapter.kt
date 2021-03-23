package com.example.gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_layout_album.view.*

class AlbumRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<AlbumRecyclerViewAdapter.AlbumViewHolder>() {

    private var albumList = ArrayList<Album>()

    inner class AlbumViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(album: Album)
        {
            view.album_name.text = album.albumName
            Glide.with(context).load(album.firstPic).into(view.row_layout_album_image_view)
            view.setOnClickListener {
                var intent = Intent(context, ImageListActivity::class.java)
                intent.putExtra(Album.ALBUM_KEY, album)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_album, parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int {
       return this.albumList.size
    }

    fun updateAlbumList(list: ArrayList<Album>)
    {
        this.albumList = list
        notifyDataSetChanged()
    }
}