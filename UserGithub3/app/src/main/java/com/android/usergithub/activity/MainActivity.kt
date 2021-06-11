package com.android.usergithub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.usergithub.adapter.Adapter
import com.android.usergithub.R
import com.android.usergithub.databinding.ActivityMainBinding
import com.android.usergithub.viewmodel.UserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var view : UserViewModel
    private lateinit var adapter : Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter()
        view = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        binding.apply {
            recyclerUser.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerUser.setHasFixedSize(true)
            recyclerUser.adapter = adapter

            btnSearch.setOnSearchClickListener {
                userSearch()
            }
            
            searchText.setOnKeyListener { _, keyCode, event ->
                if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    userSearch()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }
        view.getUser().observe(this, {
            if (it != null){
                adapter.setData(it)
                binding.imgSearch.visibility = View.INVISIBLE
                binding.txtConnectOther.visibility = View.INVISIBLE
                showProgress(false)
            }
        })
    }

    private fun userSearch() {
        binding.apply {
            val temp = searchText.text.toString()
            if (temp.isEmpty()) return
            showProgress(true)
            view.setUserSearch(temp, this@MainActivity)
        }
    }

    private fun showProgress(b: Boolean) {
        if (b){
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.INVISIBLE
        }
    }


}