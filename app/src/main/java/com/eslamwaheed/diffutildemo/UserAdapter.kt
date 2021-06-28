package com.eslamwaheed.diffutildemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eslamwaheed.diffutildemo.databinding.UserItemBinding

private const val ARG_NEW = "arg.new"

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback()) {
    lateinit var binding: UserItemBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        binding = UserItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, pos: Int) {
        onBindViewHolder(holder, pos, emptyList())
    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, pos: Int, payload: List<Any>) {
        val user = getItem(pos)
        if (payload.isEmpty() || payload[0] !is Bundle) {
            viewHolder.bind(user)
        } else {
            val bundle = payload[0] as Bundle
            viewHolder.update(bundle)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class DiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem

        override fun getChangePayload(oldItem: User, newItem: User): Any? {
            if (oldItem.id == newItem.id) {
                return if (oldItem == newItem) {
                    super.getChangePayload(oldItem, newItem)
                } else {
                    val diff = Bundle()
                    diff.putString(ARG_NEW, newItem.name)
                    diff
                }
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }

    inner class UserViewHolder(private val userItemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userItemBinding.root) {
        fun bind(user: User) {
            userItemBinding.tvName.text = user.name
        }

        fun update(bundle: Bundle) {
            if (bundle.containsKey(ARG_NEW)) {
                val name = bundle.getString(ARG_NEW)
                userItemBinding.tvName.text = name
            }
        }

    }
}