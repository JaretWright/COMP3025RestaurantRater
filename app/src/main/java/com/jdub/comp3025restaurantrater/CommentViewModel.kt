package com.jdub.comp3025restaurantrater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommentViewModel(restaurantID : String) : ViewModel() {
    private val comments = MutableLiveData<List<Comment>>()

    init{
        //query the DB (firestore) to get a list of comments
    }

    fun getComments() : LiveData<List<Comment>>{
        return comments
    }

}