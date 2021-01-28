package com.example.emitter.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.emitter.R
import com.example.emitter.models.User


/**
 *
 *Created by Atef on 27/01/21
 *
 */

@BindingAdapter("usersApiStatus")
fun bindStatus(statusImageView: ImageView, status: UsersApiStatus?) {
    when (status) {
        UsersApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        UsersApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        UsersApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UsersAdapter
    adapter.submitList(data)
}

@BindingAdapter("itemDecoration")
fun bindItemDecoration(recyclerView: RecyclerView, orientation: Int) {
    val mDividerItemDecoration = DividerItemDecoration(
        recyclerView.context,
        orientation
    )
    recyclerView.addItemDecoration(mDividerItemDecoration)
}

@BindingAdapter("loadingStatus")
fun bindLoadingStatus(swipeRefreshLayout: SwipeRefreshLayout, status: UsersApiStatus?) {
    when (status) {
        UsersApiStatus.LOADING -> swipeRefreshLayout.isRefreshing = true
        UsersApiStatus.ERROR -> swipeRefreshLayout.isRefreshing = false
        UsersApiStatus.DONE -> swipeRefreshLayout.isRefreshing = false
    }
}