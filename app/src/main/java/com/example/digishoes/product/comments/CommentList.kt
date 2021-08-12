package com.example.digishoes.product.comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.EXTRA_KEY_ID
import com.example.digishoes.data.Comment
import com.example.digishoes.databinding.ActivityCommentListBinding
import com.example.digishoes.databinding.ItemCommentsBinding
import com.example.digishoes.product.CommentAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentList : AppCompatActivity() {
    val commentViewModel: CommentsViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCommentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val commentListRv = binding.commentListRv
        commentViewModel.commentsLiveData.observe(this) {
            val commentAdapter = CommentAdapter(true)
            commentAdapter.comments = it as ArrayList<Comment>

            commentListRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            commentListRv.adapter = commentAdapter

        }
    }
}