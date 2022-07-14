package com.example.mvi.activity.main.viewMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi.activity.main.helper.MainHelper
import com.example.mvi.repository.PostRepository

class MainViewModelFactory(private var mainHelper: MainHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(PostRepository(mainHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}