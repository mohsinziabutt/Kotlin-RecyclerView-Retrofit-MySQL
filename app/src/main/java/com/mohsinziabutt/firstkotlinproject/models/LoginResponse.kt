package com.mohsinziabutt.firstkotlinproject.models

//data class LoginResponse(val error: Boolean, val message:String, val user: User)
data class LoginResponse(
    val error: Boolean,
    val message:String,
    val user: UserModel
    )