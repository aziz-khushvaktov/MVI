package com.example.mvi.activity.main.helper

import com.example.mvi.model.Post

interface MainHelper {

    suspend fun allPosts(): ArrayList<Post>
    suspend fun deletePost(id: Int): Post
}