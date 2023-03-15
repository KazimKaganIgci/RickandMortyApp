package com.kazim.rickandmorty.repo

import com.kazim.rickandmorty.api.SeriesApi
import com.kazim.rickandmorty.data.SeriesResponse
import com.kazim.rickandmorty.data.SingleCharacter
import retrofit2.Response
import javax.inject.Inject


class SeriesRespository @Inject constructor(private val seriesApi: SeriesApi) {


    suspend fun getSeries():Response<SeriesResponse>{
        return seriesApi.getSeries()
    }

    suspend fun getCharacter(id:String):Response<SingleCharacter>{
        return seriesApi.getCharacter(id)
    }

    suspend fun getLocation(no:Int): Response<SeriesResponse> {
        return seriesApi.getLocation(no)

    }
}