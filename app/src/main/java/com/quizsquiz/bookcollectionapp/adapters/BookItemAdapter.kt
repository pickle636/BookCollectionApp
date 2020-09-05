package com.quizsquiz.bookcollectionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quizsquiz.bookcollectionapp.databinding.TaskItemListBinding
import com.quizsquiz.bookcollectionapp.models.Book

class BookItemAdapter(private val clickListener:(Book) -> Unit) :
    RecyclerView.Adapter<BookItemAdapter.BookItemViewHolder>() {
    private var list = mutableListOf<Book>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemListBinding.inflate(inflater)
        return BookItemViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val book: Book = list[position]
        holder.bind(book, clickListener)
    }

    class BookItemViewHolder(private val binding: TaskItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book, clickListener: (Book) -> Unit) {
            binding.model = item
            binding.executePendingBindings()
            binding.cardView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    fun onChange(list: List<Book>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
