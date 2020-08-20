package com.quizsquiz.bookcollectionapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quizsquiz.bookcollectionapp.databinding.TaskItemListBinding
import com.quizsquiz.bookcollectionapp.models.Book

class BookItemAdapter(private val context: Context, private val list: MutableList<Book>) :
    RecyclerView.Adapter<BookItemAdapter.BookItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TaskItemListBinding.inflate(inflater)
        return BookItemViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val book: Book = list[position]
        holder.bind(book)
    }

    class BookItemViewHolder(private val binding: TaskItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

    fun onChange(list: List<Book>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
