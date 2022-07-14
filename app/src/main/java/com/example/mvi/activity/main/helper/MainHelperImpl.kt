package com.example.mvi.activity.main.helper

import com.example.mvi.model.Post
import com.example.mvi.network.service.PostService

class MainHelperImpl(private val postService: PostService): MainHelper {
    override suspend fun allPosts(): ArrayList<Post> {
        return postService.allPosts()
    }

    override suspend fun deletePost(id: Int): Post {
        return postService.deletePost(id)
    }
}