package com.jdub.comp3025restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jdub.comp3025restaurantrater.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restaurantNameTextView.text = intent.getStringExtra("restaurantName")
        val restaurantID = intent.getStringExtra("restaurantID")

    }
}