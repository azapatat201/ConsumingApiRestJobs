package com.example.consumingmyfirstapi.interfaces

import com.example.consumingmyfirstapi.model.jobsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiServices {
    @GET("{param}")
    suspend fun getAllJobs(@Path("param") param:String): Response<jobsResponse>

    @GET
    suspend fun getByLimitJobs(@Url url:String): Response<jobsResponse>

    @GET
    suspend fun getByJobs(@Url url:String): Response<jobsResponse>

    @GET
    suspend fun geBytCategoryJobs(@Url url:String): Response<jobsResponse>

    @GET
    suspend fun getByCompanyJobs(@Url url:String): Response<jobsResponse>
}