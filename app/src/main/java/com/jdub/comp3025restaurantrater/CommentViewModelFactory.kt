package com.jdub.comp3025restaurantrater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CommentViewModelFactory(private val restaurantID : String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java))
        {
            return CommentViewModel(restaurantID) as T
        }
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}