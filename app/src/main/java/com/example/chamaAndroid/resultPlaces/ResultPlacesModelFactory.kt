package com.example.chamaAndroid.resultPlaces

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ResultPlacesModelFactory (
    private val placeArgs: Bundle,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultPlacesViewModel::class.java)) {
                return ResultPlacesViewModel(placeArgs, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

