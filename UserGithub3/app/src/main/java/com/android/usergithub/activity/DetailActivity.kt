package com.android.usergithub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.android.usergithub.R
import com.android.usergithub.adapter.SectionsPagerAdapter
import com.android.usergithub.databinding.ActivityDetailBinding
import com.android.usergithub.model.UserList
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            getUser(username)
        }



        val backButton : ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun getUser(username : String) {
        binding.progress.visibility = View.VISIBLE
        var user : UserList
        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$username"
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                binding.progress.visibility = View.INVISIBLE
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val name = jsonObject.getString("name")
                    val login = jsonObject.getString("login")
                    val avatar = jsonObject.getString("avatar_url")
                    val repo = jsonObject.getString("public_repos")
                    val location = jsonObject.getString("location")
                    val company = jsonObject.getString("company")
                    val followers = jsonObject.getString("followers")
                    val following = jsonObject.getString("following")

                    user = UserList(name = name, login = login, avatar_url = avatar, repo = repo, location = location, company = company,followers = followers, following = following)
                    setLayout(user)

                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                binding.progress.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setLayout(user : UserList){
        binding.progress.visibility = View.INVISIBLE
        binding.name.text = user.name
        binding.username.text = user.login
        binding.city.text = user.location
        binding.company.text = user.company
        binding.repo.text = user.repo
        binding.textView2.text = user.followers
        binding.following.text = user.following
        Glide.with(this)
            .load(user.avatar_url)
            .into(binding.imageBackground)
    }

}