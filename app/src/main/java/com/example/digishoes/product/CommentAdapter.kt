package com.example.digishoes.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.data.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTitle: TextView = itemView.findViewById(R.id.commentTitleTv)
        val commentDate: TextView = itemView.findViewById(R.id.commentDateTv)
        val commentAuthor: TextView = itemView.findViewById(R.id.commentAuthorTv)
        val commentContent: TextView = itemView.findViewById(R.id.commentContentTv)
        fun bind(comment: Comment) {
            commentTitle.text = comment.title
            commentAuthor.text = comment.author.email
            commentContent.text = comment.content
            commentDate.text = comment.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comments, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
        return if (comments.size > 3) 3 else comments.size
    }
}