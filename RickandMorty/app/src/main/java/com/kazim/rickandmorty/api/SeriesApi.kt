package com.kazim.rickandmorty.api

import com.kazim.rickandmorty.data.SeriesResponse
import com.kazim.rickandmorty.data.SingleCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("api/location")
    suspend fun getSeries(): Response<SeriesResponse>



    @GET("api/character/{id}")
    suspend fun getCharacter(
        @Path("id") id: String
    ): Response<SingleCharacter>



    @GET("api/location?")
    suspend fun getLocation(
        @Query("page") page:Int
    ):Response<SeriesResponse>
}