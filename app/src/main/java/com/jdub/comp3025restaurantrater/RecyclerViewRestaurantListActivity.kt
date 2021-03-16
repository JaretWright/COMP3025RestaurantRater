package com.jdub.comp3025restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jdub.comp3025restaurantrater.databinding.ActivityRecyclerViewRestaurantListBinding

class RecyclerViewRestaurantListActivity : AppCompatActivity(), RecyclerViewAdapter.RestaurantItemListener {
    private lateinit var binding : ActivityRecyclerViewRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the data from the viewmodel
        val model : RestaurantListViewModel by viewModels()
        model.getRestaurants().observe(this, Observer<List<Restaurant>>{ restaurants->
            var recyclerAdapter = RecyclerViewAdapter(this, restaurants, this)
            binding.verticalRecyclerView.adapter = recyclerAdapter
        })

        //setup a click listener for the floating action button to add a new restaurant
        binding.addRestaurantFAB.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun restaurantSelected(restaurant: Restaurant) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("restaurantID", restaurant.id)
        intent.putExtra("restaurantName", restaurant.name)
        startActivity(intent)
    }
}