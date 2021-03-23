package com.example.gallery

import java.io.Serializable

data class Album(
        var albumName: String,
        var firstPic: String,
        var images: List<String>
): Serializable
{
        companion object{
                const val ALBUM_KEY = "Album"
        }
}