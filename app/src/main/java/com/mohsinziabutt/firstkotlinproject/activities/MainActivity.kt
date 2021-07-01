package com.mohsinziabutt.firstkotlinproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsinziabutt.firstkotlinproject.R
import com.mohsinziabutt.firstkotlinproject.adapters.PostAdapter
import com.mohsinziabutt.firstkotlinproject.api.GetPostsClient
import com.mohsinziabutt.firstkotlinproject.models.PostResponse
import com.mohsinziabutt.firstkotlinproject.models.PostsArrayList
import com.mohsinziabutt.firstkotlinproject.prefmanager.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    lateinit var postAdapter: PostAdapter
    var postsArrayList: ArrayList<PostsArrayList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        GetPostsClient.instance.getAllPosts().enqueue(object: Callback<List<PostResponse>> {
                override fun onResponse(
                    call: Call<List<PostResponse>>,
                    response: Response<List<PostResponse>>
                ) {
                    val allPosts = response.body()
                    if(allPosts != null)
                    {
                        Log.d("RESPONSE::", "" + response.body())

                        for (i in 0 until allPosts.count())
                        {
                            val id = allPosts[i].id ?: "N/A"
                            val title = allPosts[i].title ?: "N/A"
                            val body = allPosts[i].body ?: "N/A"

                            val singlePostModel = PostsArrayList(id.toString(), title, body)

                            postsArrayList.add(singlePostModel)
                            postAdapter = PostAdapter(this@MainActivity, postsArrayList)
                            postAdapter.notifyDataSetChanged()

                        }
                        recyclerView.adapter = postAdapter
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "no data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })


////        just for testing
//        GetPostsClient.instance.getAllPosts().enqueue(object: Callback<List<PostResponse>> {
//            override fun onResponse(call: Call<List<PostResponse>>, response: Response<List<PostResponse>>) {
//
//                val allPosts = response.body()
//                if(allPosts != null)
//                {
//                    //to check log
//                    Log.d("POST_RESPONSE", "" + response.body())
//                }
//                else
//                {
//                    Toast.makeText(applicationContext, "no data", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
//                //to check log
//                Log.d("POST_RESPONSE", "" + t.message)
//                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//            }
//
//        })
    }
}