package com.lh12345678.comp3025restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.lh12345678.comp3025restaurantrater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveRestaurantButton.setOnClickListener {

            //check to make sure the fields are populated
            if (binding.restaurantEditText.text.toString().isNotEmpty() && binding.ratingSpinner.selectedItemPosition>0)
            {
                val restaurant = Restaurant()
                restaurant.name = binding.restaurantEditText.text.toString()
                restaurant.rating = binding.ratingSpinner.selectedItem.toString().toInt()

                val db = FirebaseFirestore.getInstance().collection("restaurants")
                restaurant.id = db.document().id

                //our restaurant now has a name, id and rating - so we can push it to the DB
                db.document(restaurant.id!!).set(restaurant)
                        .addOnSuccessListener {
                            //show confirmation and clear the fields
                            Toast.makeText(this, "Restaurant Added", Toast.LENGTH_LONG).show()

                            //navigate back to the recyclerview list of restaurants
                            val intent = Intent(this, RestaurantRecyclerListActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
            }
            else
            {
                Toast.makeText(this, "Restaurant name & rating required", Toast.LENGTH_LONG).show()
            }
        }
    }
}