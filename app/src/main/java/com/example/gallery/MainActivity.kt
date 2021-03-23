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

    lateinit var listAdapter: ImagesRecyclerViewAdapter
    val pathlist = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        listAdapter = ImagesRecyclerViewAdapter(this)
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
        contentResolver.query(uri, projections,null, null, null)
                .use {
                    while(it?.moveToNext() == true)
                    {
                        pathlist.add(it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)))
                        Log.d("abc", it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)))
                    }
                    listAdapter.updateList(pathlist)
                }
    }
}