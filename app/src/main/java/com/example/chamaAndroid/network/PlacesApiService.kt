package com.example.chamaAndroid.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private const val BASE_URL = "https://mars.udacity.com/"
enum class PlacesApiFilter(val value: String) { SHOW_CAFES("Cafes"), SHOW_BARS("Bars"), SHOW_RESTAURANTS("Restaurants") }

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface PlacesApiService {
    /**
     * Returns a Coroutine [List] of [PlacesProperty] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("results")
    suspend fun getProperties(@Query("filter") type: String): List<PlacesProperty>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PlacesApi {
    val retrofitService : PlacesApiService by lazy { retrofit.create(PlacesApiService::class.java) }
}