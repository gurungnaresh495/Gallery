package com.example.gallery

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: AlbumRecyclerViewAdapter
    val list = ArrayList<Album>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        listAdapter = AlbumRecyclerViewAdapter(this)
        recycler_view_images.adapter = listAdapter
        recycler_view_images.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        requestPermissionForGallery()

    }

    private fun requestPermissionForGallery() {
        if (ContextCompat.checkSelfPermission(baseContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED)
        {
            Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            getAllPictures()
                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                        }

                        override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {

                        }

                    }).check()
        }
        else
        {
            getAllPictures()
        }
    }

    fun getAllPictures()
    {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projections = arrayOf(MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        contentResolver.query(uri, projections,null, null, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                .use {
                    var firstImage: String
                    var imageList = ArrayList<String>()
                    var album: String
                    var current = ""
                    while(it?.moveToNext() == true)
                    {
                        var image = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                        album = it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))

                        if(current != album)
                        {
                            firstImage = image
                            current =album
                            var newAlbum = Album(album, firstImage, imageList)
                            list.add(newAlbum)
                            imageList.clear()
                        }
                        imageList.add(image)

                        Log.d("abc", it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)))
                        Log.d("abc", it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)))
                    }
                    listAdapter.updateAlbumList(list)
                }
    }
}