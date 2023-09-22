package com.akshay.evaluation7.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akshay.evaluation7.databinding.BooksCardViewBinding
import com.akshay.evaluation7.databinding.CardReadBooksBinding
import com.akshay.evaluation7.model.BooksEntity

class BooksReadAdapter() : ListAdapter<BooksEntity, BooksReadAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: CardReadBooksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BooksEntity) {
            binding.apply {
                authorNameTextView.text = item.author
                bookTitleTextView.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CardReadBooksBinding = CardReadBooksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BooksEntity>() {
            override fun areItemsTheSame(oldItem: BooksEntity, newItem: BooksEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BooksEntity, newItem: BooksEntity): Boolean {
                return areItemsTheSame(oldItem,newItem)
            }
        }
    }
}