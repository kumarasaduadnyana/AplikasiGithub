package com.android.usergithub.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.usergithub.model.UserList
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class UserViewModel : ViewModel() {
    val userList = MutableLiveData<ArrayList<UserList>>()

    fun setUserSearch(q:String, context: Context) {
        val listItems = ArrayList<UserList>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/search/users?q=$q"
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONObject(result).getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val username = jsonArray.getJSONObject(i).getString("login")
                        val avatarUrl = jsonArray.getJSONObject(i).getString("avatar_url")
                        listItems.add(UserList(login = username, avatar_url = avatarUrl))
                    }
                    userList.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFollower(q:String, context: Context) {
        val listItems = ArrayList<UserList>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$q/followers"
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val username = jsonArray.getJSONObject(i).getString("login")
                        val avatarUrl = jsonArray.getJSONObject(i).getString("avatar_url")
                        listItems.add(UserList(login = username, avatar_url = avatarUrl))
                    }
                    userList.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFollowing(q:String, context: Context) {
        val listItems = ArrayList<UserList>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$q/following"
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val username = jsonArray.getJSONObject(i).getString("login")
                        val avatarUrl = jsonArray.getJSONObject(i).getString("avatar_url")
                        listItems.add(UserList(login = username, avatar_url = avatarUrl))
                    }
                    userList.postValue(listItems)
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUser() : LiveData<ArrayList<UserList>> {
        return userList
    }
}