package com.mohsinziabutt.firstkotlinproject.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.mohsinziabutt.firstkotlinproject.R
import com.mohsinziabutt.firstkotlinproject.api.LoginRegistrationApiClient
import com.mohsinziabutt.firstkotlinproject.models.LoginResponse
import com.mohsinziabutt.firstkotlinproject.prefmanager.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity()
{
//    var isNightTheme = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        btnRegSwitch.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }

        buttonLogin.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if(email.isEmpty()){
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            LoginRegistrationApiClient.instance.userLogin(email, password)
                .enqueue(object: Callback<LoginResponse> {

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(!response.body()?.error!!)
                        {
                            //to check log
                            Log.d("LOGIN_RESPONSE", "" + response.body())

                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else
                        {
                            //to check log
                            Log.d("LOGIN_RESPONSE", "" + response.body())

                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here.
//        val id = item.getItemId()
//
//        if (id == R.id.action_one)
//        {
//            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
//            return true
//        }
//
//        if (id == R.id.action_two)
//        {
//            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}