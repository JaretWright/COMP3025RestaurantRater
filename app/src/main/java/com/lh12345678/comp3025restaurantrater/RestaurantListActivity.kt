package com.lh12345678.comp3025restaurantrater

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lh12345678.comp3025restaurantrater.databinding.ActivityRestaurantListBinding

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : RestaurantListViewModel by viewModels()
        model.getRestaurants().observe( this, {
            //clear the exisiting LinearLayout
            binding.linearLayout.removeAllViews()

            for (restaurant in it)
            {
                val textView = TextView(this)
                textView.text = restaurant.name
                textView.textSize = 20f
                binding.linearLayout.addView(textView)
            }
        }

        )
    }
}