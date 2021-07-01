package com.mohsinziabutt.firstkotlinproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mohsinziabutt.firstkotlinproject.R
import com.mohsinziabutt.firstkotlinproject.api.LoginRegistrationApiClient
import com.mohsinziabutt.firstkotlinproject.models.DefaultResponse
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnLoginSwitch.setOnClickListener {
            onBackPressed()
        }

        buttonSignUp.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()

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

            if(name.isEmpty()){
                editTextName.error = "Name required"
                editTextName.requestFocus()
                return@setOnClickListener
            }

            LoginRegistrationApiClient.instance.createUser(email, name, password)
                .enqueue(object: Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }

                })

        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        if(SharedPrefManager.getInstance(this).isLoggedIn){
//            val intent = Intent(applicationContext, ProfileActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//            startActivity(intent)
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}