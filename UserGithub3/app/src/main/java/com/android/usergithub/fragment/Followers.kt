package com.android.usergithub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.usergithub.adapter.Adapter
import com.android.usergithub.activity.DetailActivity
import com.android.usergithub.databinding.FragmentFollowersBinding
import com.android.usergithub.viewmodel.UserViewModel

class Followers : Fragment() {

    private lateinit var view : UserViewModel
    private lateinit var adapter : Adapter

    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            adapter = Adapter()
            this.view = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

            binding.apply {
                recyclerUser.layoutManager = LinearLayoutManager(context)
                recyclerUser.setHasFixedSize(true)
                recyclerUser.adapter = adapter

            }
            val username = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)
            context?.let {
                if (username != null) {
                    this.view.getFollower(username, it)
                }
            }
            this.view.getUser().observe(this, {
                if (it != null){
                    adapter.setData(it)
                }
            })
        }
    }

}