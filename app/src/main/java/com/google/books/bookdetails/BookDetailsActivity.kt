package com.google.books.bookdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.books.R
import com.google.books.data.Volumes
import kotlinx.android.synthetic.main.activity_book_details.*

class BookDetailsActivity : AppCompatActivity() {

    companion object {
        const val ITEM_KEY = "item_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        setupViews()
    }

    private fun setupViews() {

        val item = intent.extras?.getSerializable(ITEM_KEY) as Volumes.Item
        bookTitle.text = item.volumeInfo.title

        item.volumeInfo.authors?.let {
            author.text = it[0]
        }

        item.volumeInfo.imageLinks?.let{
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this)
                .load(it.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions).into(thumbnail)
        }

        item.volumeInfo.publisher?.let {
            publisher.text = it
        }

        item.volumeInfo.categories?.let {
            category.text = it[0]
        }

        ratingBar.rating = if (item.volumeInfo.averageRating != null) item.volumeInfo.averageRating.toFloat() else 0f

        pageCount.text = item.volumeInfo.pageCount.toString()

        item.volumeInfo.description?.let {
            description.text = it
        }?: run {
            aboutTitle.visibility = View.GONE
        }
    }
}
