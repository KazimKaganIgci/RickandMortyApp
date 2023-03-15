package com.kazim.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazim.rickandmorty.data.Result
import com.kazim.rickandmorty.data.SingleCharacter
import com.kazim.rickandmorty.repo.SeriesRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val seriesRespository: SeriesRespository):ViewModel(){


    init {
        getSeriesList()
    }
    private val _getSeriesLiveData =MutableLiveData<List<Result>>()
    val getSeriesLiveData: LiveData<List<Result>> =_getSeriesLiveData

    fun getSeriesList(){
        viewModelScope.launch {
            val response =seriesRespository.getSeries()
            if (response.isSuccessful){
                response.body()?.results.let {
                    _getSeriesLiveData.postValue(it as List<Result>)
                }
            }
        }
    }


    private val _getCharacterLiveData =MutableLiveData<SingleCharacter>()
    val getCharacterLiveData: LiveData<SingleCharacter> =_getCharacterLiveData

    fun getCharacterData(id:String){
        viewModelScope.launch {
            val response =seriesRespository.getCharacter(id)
            if (response.isSuccessful){
                response.body()?.let {
                    _getCharacterLiveData.postValue(it)
                }
            }
        }
    }


    private val _getLocationLiveData =MutableLiveData<List<Result>>()
    val getLocationLiveData: LiveData<List<Result>> = _getLocationLiveData
    fun getLocationData(no:Int){
        viewModelScope.launch {
            val response =seriesRespository.getLocation(no)
            if (response.isSuccessful){
                response.body()?.let {
                    _getLocationLiveData.postValue(it.results as List<Result>)

                }
            }
        }
    }

}