package com.example.listpoke.repository

data class Resource<T>(
    val data: T?,
    val error: String? = null
)