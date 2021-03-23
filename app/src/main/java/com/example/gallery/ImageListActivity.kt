package com.example.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_image_list.*

class ImageListActivity : AppCompatActivity() {

    private lateinit var listAdapter: ImagesRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        init()
    }

    private fun init() {
        listAdapter = ImagesRecyclerViewAdapter(this)
        image_list_recycler_view.adapter = listAdapter
        image_list_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        var album = intent.getSerializableExtra(Album.ALBUM_KEY) as Album
        listAdapter.updateList(album.images as ArrayList<String>)
    }
}