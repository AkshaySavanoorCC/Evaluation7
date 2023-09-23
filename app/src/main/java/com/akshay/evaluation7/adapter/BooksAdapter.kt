package com.akshay.evaluation7.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akshay.evaluation7.databinding.BooksCardViewBinding
import com.akshay.evaluation7.model.BooksEntity
import android.widget.Switch
import android.widget.TextView

class BooksAdapter(private val onToggleChanged :(BooksEntity,Boolean) -> Unit,private val onDelete :(BooksEntity) -> Unit) : ListAdapter<BooksEntity, BooksAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: BooksCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val isReadToggle: Switch = binding.isReadToggle
        val delete : TextView = binding.deleteBook

        fun bind(item: BooksEntity) {
            binding.apply {
                authorNameTextView.text = item.author
                bookTitleTextView.text = item.title
                isReadToggle.isChecked = item.isRead
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: BooksCardViewBinding = BooksCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.delete.setOnClickListener { onDelete(current) }
        holder.isReadToggle.setOnCheckedChangeListener { _, isChecked ->
                onToggleChanged(current,isChecked)
        }
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
