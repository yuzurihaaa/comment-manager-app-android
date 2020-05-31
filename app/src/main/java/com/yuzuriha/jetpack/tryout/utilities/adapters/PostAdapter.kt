package com.yuzuriha.jetpack.tryout.utilities.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuzuriha.jetpack.tryout.databinding.PostItemBinding
import com.yuzuriha.jetpack.tryout.model.PostItem
import com.yuzuriha.jetpack.tryout.view.PostsFragmentDirections

class PostAdapter :
    ListAdapter<PostItem, PostAdapter.ViewHolder>(PostDiffCallback()) {

    inner class ViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val action = PostsFragmentDirections.actionPostsToComments(binding.post)
            v?.findNavController()?.navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        PostItemBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.post = getItem(position)
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostItem>() {

    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem.id == newItem.id
    }
}