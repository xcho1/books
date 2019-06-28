package com.google.books.books

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.books.R
import com.google.books.data.Volumes
import com.google.books.bookdetails.BookDetailsActivity
import com.google.books.databinding.BooksItemBinding
import kotlinx.android.synthetic.main.books_item.view.*

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val items = mutableListOf<Volumes.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.books_item, parent, false) as BooksItemBinding
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun updateItems(newItems: List<Volumes.Item>, isNewQuery: Boolean) {
        val oldSize = items.size
        if (isNewQuery) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            items.addAll(newItems)
            notifyItemRangeChanged(oldSize - 1, newItems.size)
        }
    }

    class ViewHolder(binding: BooksItemBinding): RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: Volumes.Item

        init {
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, BookDetailsActivity::class.java)
                intent.putExtra(BookDetailsActivity.ITEM_KEY, item)
                ActivityCompat.startActivity(itemView.context, intent, null)
            }
        }

        fun bind(item: Volumes.Item) {
            this.item = item
            itemView.title.text = item.volumeInfo.title
            item.volumeInfo.authors?.let{itemView.author.text = it[0]}
            item.volumeInfo.imageLinks?.let {
                val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(itemView.context)
                    .load(it.thumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions).into(itemView.thumbnail)
            }
        }
    }
}