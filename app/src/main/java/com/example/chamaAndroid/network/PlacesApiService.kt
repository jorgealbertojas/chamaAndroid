package com.example.chamaAndroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
enum class PlacesApiFilter(val value: String) { SHOW_CAFES("Cafes"), SHOW_BARS("Bars"), SHOW_RESTAURANTS("Restaurants") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PlacesApiService {
    @GET("textsearch/json")
    suspend fun getProperties(
        @Query("query") query: String,
        @Query("locationbias") locationbias: String,
        @Query("key") key: String
    ): ListWrapper<PlacesProperty>
}

object PlacesApi {
    val retrofitService : PlacesApiService by lazy { retrofit.create(PlacesApiService::class.java) }
}