package com.lh12345678.comp3025restaurantrater

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        //Navigate to a Maps activity
        binding.mapsFAB.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        setSupportActionBar(binding.mainToolBar)
    }

    //This method will connect the main_menu.xml file with the menu in the toolbar.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //this method will allow the user to select items from the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                return true
            }
            R.id.action_list -> {
                startActivity(Intent(applicationContext, RestaurantRecyclerListActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}