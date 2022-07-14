package com.example.mvi.repository

import com.example.mvi.activity.main.helper.MainHelper

class PostRepository(private val mainHelper: MainHelper) {

    suspend fun allPosts() = mainHelper.allPosts()
    suspend fun deletePost(id: Int) = mainHelper.deletePost(id)
}