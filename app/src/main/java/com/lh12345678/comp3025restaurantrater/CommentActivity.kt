package com.lh12345678.comp3025restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.lh12345678.comp3025restaurantrater.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommentBinding

    //define the ViewModel and the ViewModelFactory
    private lateinit var viewModel : CommentViewModel
    private lateinit var viewModelFactory : CommentViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restaurantNameTextView.text = intent.getStringExtra("restaurantName")
        val restaurantID = intent.getStringExtra("restaurantID")

        //saving a new comment
        binding.saveCommentButton.setOnClickListener {
            if (binding.usernameEditText.text.toString().isNotEmpty() && binding.bodyEditText.text.toString().isNotEmpty())
            {
                //save the comment to the DB
                val db = FirebaseFirestore.getInstance().collection("comments")
                val id = db.document().id

                val newComment = Comment(id, binding.usernameEditText.text.toString(), binding.bodyEditText.text.toString(),restaurantID)
                db.document(id).set(newComment)
                    .addOnSuccessListener { Toast.makeText(this, "Added to DB", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener { Toast.makeText(this, "Failed to add", Toast.LENGTH_LONG).show() }
            }
            else
            {
                Toast.makeText(this,"Both username and comment must be populated", Toast.LENGTH_LONG).show()
            }
        }

        //configure the recyclerView with the ViewModelFactory and ViewModel
        restaurantID?.let {
            viewModelFactory = CommentViewModelFactory(restaurantID)

            //connect the viewmodel with the activity
            viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
            viewModel.getComments().observe(this, Observer<List<Comment>>{ comments ->
                var recyclerAdapter = CommentViewAdapter(this, comments)
                binding.commentsRecyclerView.adapter = recyclerAdapter
            })
        }

        binding.backToRecyclerViewFAB.setOnClickListener {
            finish()
        }

    }
}