package com.android.usergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.usergithub.activity.DetailActivity
import com.android.usergithub.databinding.ItemListBinding
import com.android.usergithub.model.UserList
import com.bumptech.glide.Glide

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val userList = ArrayList<UserList>()

    fun setData(items: ArrayList<UserList>) {
        userList.clear()
        userList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewList = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(viewList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(userList[position])
    }

    override fun getItemCount() = userList.size

    inner class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(user: UserList){
            binding.apply {
                Glide.with(itemView)
                        .load(user.avatar_url)
                        .into(imageView)
                username.text = user.login
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME,user.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}

