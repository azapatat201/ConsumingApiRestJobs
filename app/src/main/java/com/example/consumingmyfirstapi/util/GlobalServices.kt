package com.example.consumingmyfirstapi.util

import android.util.Log
import com.example.consumingmyfirstapi.interfaces.ApiServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://remotive.io/api/remote-jobs/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
