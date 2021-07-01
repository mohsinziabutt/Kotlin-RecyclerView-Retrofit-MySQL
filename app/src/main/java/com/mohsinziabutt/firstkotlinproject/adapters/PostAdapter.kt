package com.mohsinziabutt.firstkotlinproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohsinziabutt.firstkotlinproject.R
import com.mohsinziabutt.firstkotlinproject.models.PostsArrayList

// if we use val or var in constructor variables act as properties
// and without var or val variables act as parameters
class PostAdapter(val context: Context, val posts: List<PostsArrayList>) :
    RecyclerView.Adapter<PostAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        holder.id.text = post.post_id + "."
        holder.title.text = post.post_title
        holder.body.text = post.post_body
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id = itemView.findViewById<TextView>(R.id.id)
        var title = itemView.findViewById<TextView>(R.id.title)
        var body = itemView.findViewById<TextView>(R.id.body)
    }
}