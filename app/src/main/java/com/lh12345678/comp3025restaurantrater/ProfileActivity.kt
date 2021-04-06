package com.lh12345678.comp3025restaurantrater

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.lh12345678.comp3025restaurantrater.databinding.ActivityProfileBinding
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val authDb = FirebaseAuth.getInstance()

    //these variables are used for handling the profile picture
    private val REQUEST_CODE = 1000
    private lateinit var filePhoto : File
    private val FILE_NAME = "photo"

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

        //ensure the device has the correct permissions
        //So if we don't have permission we request for permissions from the user, this will execute the overridden onRequestPermissionsResult
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) || checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                    arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
            )
        }

        //add a click listener for the camera button
        binding.imageButton.setOnClickListener {
            //create an Intent to navigate to the camera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            //add the file location to the intent
            filePhoto = getPhotoFile(FILE_NAME)

        }

        //connect the logout FAB to the logout method
        binding.logoutFAB.setOnClickListener {
            logout()
        }

        setSupportActionBar(binding.mainToolBar.topToolbar)
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

    //This method will return the file object for the picture (the actual .jpg)
    private fun getPhotoFile(fileName: String) : File{
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }
}