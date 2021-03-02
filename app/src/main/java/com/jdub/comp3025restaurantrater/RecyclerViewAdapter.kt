package com.jdub.comp3025restaurantrater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (val context: Context,
                           val restaurants : List<Restaurant>) : RecyclerView.Adapter<RecyclerViewAdapter.RestaurantViewHolder>()
{
   //the goal of this class is to create a way to access the variables/views in the item_restaurant.xml layout file
   inner class RestaurantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
       val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
       val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
   }

    //this method will tell the adapter how many items need to be inflated
    override fun getItemCount(): Int {
        return restaurants.size
    }

    /**
     * This connects (aka "inflates") the individual ViewHolder (which is the link to the item_restaurant.xml)
     * with the RecyclerView object
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    /**
     * This binds the actual data to the view
     */
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        with (holder){
            nameTextView.text = restaurant.name
            ratingBar.rating = restaurant.rating!!.toFloat()
        }
    }




}