package com.jdub.comp3025restaurantrater

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RestaurantListViewModel : ViewModel() {

    //private instance variable to keep track of the changable data
    private val restaurants = MutableLiveData<List<Restaurant>>()

    init {
        loadRestaurants()
    }

    /**
     * This method will return a list of Restaurant objects.  If there are any changes to the DB, it
     * up automatically update the list.
     * https://firebase.google.com/docs/firestore/query-data/listen
     */
    private fun loadRestaurants(){
        val db = FirebaseFirestore.getInstance().collection("restaurants")
                    .orderBy("name", Query.Direction.ASCENDING)

        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returneed ${documents?.size()}")

            //if an exception was returned, log it
            if (exception != null){
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val restaurantList = ArrayList<Restaurant>()

            //.let is used to ensure that the documents object is not null
            documents?.let {
                //Loop over the documents returned and update the list of restaurants
                for (document in documents) {
                    val restaurant = document.toObject(Restaurant::class.java)
                    restaurantList.add(restaurant)
                }
                restaurants.value = restaurantList
            }


            }
        }
    }

}