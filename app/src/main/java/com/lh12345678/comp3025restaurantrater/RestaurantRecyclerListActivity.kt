package com.lh12345678.comp3025restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lh12345678.comp3025restaurantrater.databinding.ActivityRecyclerViewRestaurantListBinding

class RestaurantRecyclerListActivity : AppCompatActivity(), RecyclerViewAdapter.RestaurantItemListener {
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
        setSupportActionBar(binding.mainToolBar.toolbar)
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
//                startActivity(Intent(applicationContext, RestaurantRecyclerListActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun restaurantSelected(restaurant: Restaurant) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("restaurantID", restaurant.id)
        intent.putExtra("restaurantName", restaurant.name)
        startActivity(intent)
    }
}