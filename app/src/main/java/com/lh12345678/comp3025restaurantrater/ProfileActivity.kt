package com.lh12345678.comp3025restaurantrater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.lh12345678.comp3025restaurantrater.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val authDb = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //check that we have a valid user.  If not, go to login screen
        if (authDb.currentUser == null)
            logout()

        //update the user's name and email address
        authDb.currentUser?.let{ user->
            binding.userNameTextView.text = user.displayName
            binding.emailTextView.text = user.email
        }

        //enable the scroll bars on the textview
        binding.termsTextView.movementMethod = ScrollingMovementMethod()

        //connect the logout FAB to the logout method
        binding.logoutFAB.setOnClickListener {
            logout()
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
//                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        authDb.signOut()
        finish()
        startActivity(Intent(this, SignInActivity::class.java))
    }

}