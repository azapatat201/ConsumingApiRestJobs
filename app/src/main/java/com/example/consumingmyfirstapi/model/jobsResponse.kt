package com.example.consumingmyfirstapi.model


import com.google.gson.annotations.SerializedName

data class jobsResponse(
    @SerializedName("job-count")
    val jobCount: Int,
    @SerializedName("jobs")
    val jobs: List<Jobs>,
    @SerializedName("0-legal-notice")
    val legalNotice: String
)