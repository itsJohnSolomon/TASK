package com.jojo.cloudintask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jojo.cloudintask.R
import com.jojo.cloudintask.adapters.UserPagingAdapter
import com.jojo.cloudintask.databinding.ActivityMainBinding
import com.jojo.cloudintask.viewModels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var quoteViewModel: UserViewModel
    lateinit var adapter: UserPagingAdapter
    lateinit var bacBtn: ImageView
    lateinit var title: TextView
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bacBtn = findViewById(R.id.backBtn)
        title = findViewById(R.id.pageName)
        bacBtn.setImageResource(R.drawable.ic_baseline_home_24)
        title.text = "DASHBOARD"

        quoteViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        adapter = UserPagingAdapter(this)

        binding.userList.layoutManager = LinearLayoutManager(this)
        binding.userList.setHasFixedSize(true)
        binding.userList.adapter = adapter

        quoteViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        }
        )
    }
}