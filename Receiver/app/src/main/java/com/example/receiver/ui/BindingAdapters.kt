package com.example.receiver.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.receiver.models.User
import com.example.receiver.ui.main.listfragment.UsersAdapter


/**
 *
 *Created by Atef on 27/01/21
 *
 */


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

