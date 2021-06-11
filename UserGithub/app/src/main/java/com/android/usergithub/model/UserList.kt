package com.android.usergithub.model

data class UserList(
    val name : String = "",
    val login : String = "",
    val avatar_url : String = "",
    val repo : String = "",
    val location : String = "",
    val company : String = "",
    val followers : String = "",
    val following : String = "",
)
