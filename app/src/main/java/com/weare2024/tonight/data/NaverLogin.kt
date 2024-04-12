package com.weare2024.tonight.data

data class NaverLogin(val resultcode: String, val message: String, val response: Response)

data class Response(val id: String, val email: String)
