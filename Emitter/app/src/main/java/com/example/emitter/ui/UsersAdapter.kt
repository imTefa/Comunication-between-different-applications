package com.example.emitter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emitter.databinding.ListItemUserBinding
import com.example.emitter.models.User

/**
 *
 *Created by Atef on 27/01/21
 *
 */
class UsersAdapter(var onClickListener: OnClickListener) :
    ListAdapter<User, UsersAdapter.UserHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(user) }
        holder.bind(user)
    }

    class UserHolder(var binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class OnClickListener(var click: (User) -> Unit) {
        fun onClick(user: User) = click(user)
    }
}