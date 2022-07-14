package com.example.mvi.activity.main

sealed class MainIntent {
    object AllPosts: MainIntent()
    object DeletePost: MainIntent()
}