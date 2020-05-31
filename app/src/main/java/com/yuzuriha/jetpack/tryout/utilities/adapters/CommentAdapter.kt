package com.yuzuriha.jetpack.tryout.utilities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuzuriha.jetpack.tryout.databinding.CommentItemBinding
import com.yuzuriha.jetpack.tryout.model.CommentsItem

class CommentAdapter :
    ListAdapter<CommentsItem, CommentAdapter.ViewHolder>(CommentDiffCallback()) {

    inner class ViewHolder(val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        CommentItemBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.comment = getItem(position)
    }
}

private class CommentDiffCallback : DiffUtil.ItemCallback<CommentsItem>() {

    override fun areItemsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
        return oldItem.id == newItem.id
    }
}