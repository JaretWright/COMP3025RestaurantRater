package com.jdub.comp3025restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jdub.comp3025restaurantrater.databinding.ActivityRecyclerViewRestaurantListBinding

class RecyclerViewRestaurantListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerViewRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the data from the viewmodel
        val model : RestaurantListViewModel by viewModels()
        model.getRestaurants().observe(this, Observer<List<Restaurant>>{ restaurants->
            var recyclerAdapter = RecyclerViewAdapter(this, restaurants)
            binding.verticalRecyclerView.adapter = recyclerAdapter
        })
    }
}