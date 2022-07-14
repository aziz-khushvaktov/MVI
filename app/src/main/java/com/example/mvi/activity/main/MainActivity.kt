package com.example.mvi.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi.activity.main.helper.MainHelperImpl
import com.example.mvi.activity.main.viewMode.MainViewModel
import com.example.mvi.activity.main.viewMode.MainViewModelFactory
import com.example.mvi.adapter.PostAdapter
import com.example.mvi.databinding.ActivityMainBinding
import com.example.mvi.model.Post
import com.example.mvi.network.RetrofitBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    is MainState.Init -> {
                        Log.d("MainActivity", "Init")
                    }
                    is MainState.Loading -> {
                        Log.d("MainActivity", "Loading")
                    }
                    is MainState.AllPosts -> { it
                        refreshAdapter(it.posts)
                        Log.d("MainActivity", "AllPosts" + it.posts.size)
                    }
                    is MainState.DeletePost -> { it
                        intentAllPosts()
                        Log.d("MainActivity", "DeletePost" + it.post)
                    }
                    is MainState.Error -> { it
                        Log.d("MainActivity", "Error" + it)
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = MainViewModelFactory(MainHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity,1)
        }
        intentAllPosts()
    }

    private fun intentAllPosts() {
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.AllPosts)
        }
    }

    private fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter(this, posters)
        binding.recyclerView.adapter = adapter
    }

    fun intentDeletePost(id: Int) {
        lifecycleScope.launch {
            viewModel.postId = id
            lifecycleScope.launch {
                viewModel.mainIntent.send(MainIntent.DeletePost)
            }
        }
    }
}