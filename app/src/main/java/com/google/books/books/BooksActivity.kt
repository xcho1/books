package com.google.books.books

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.books.*
import com.google.books.databinding.ActivityBooksBinding
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.books.di.AppComponent
import com.google.books.di.DaggerAppComponent

class BooksActivity : AppCompatActivity() {

    companion object {
        private const val NUMBER_OF_COLUMNS = 3
        private const val PRELOAD_NUMBER = 6
    }

    private lateinit var binding: ActivityBooksBinding
    private lateinit var appComponent: AppComponent

    @Inject
    internal lateinit var vmFactory: BooksViewModel.Factory

    private val viewModel: BooksViewModel by injectViewModel { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
        appComponent.inject(this)

        lifecycle.addObserver(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_books)
        binding.recyclerView.layoutManager = GridLayoutManager(this,
            NUMBER_OF_COLUMNS
        )
        binding.viewModel = viewModel

        setupSearchBar()
        setupRecyclerView()

        viewModel.result.observe(this, Observer<BooksViewModel.Result> {

            progressBar.visibility = View.GONE

            if (it == BooksViewModel.Result.ERROR_NETWORK) {
                Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show()
            }
            if (it == BooksViewModel.Result.ERROR_UNKNOWN ) {
                Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSearchBar() {

        searchText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText.clearFocus()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(searchText.windowToken, 0)
                    val queryText = searchText.text.toString()

                    if (!queryText.isBlank()) {
                        progressBar.visibility = View.VISIBLE
                        viewModel.query(queryText)
                    }
                    return true
                }
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, NUMBER_OF_COLUMNS)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = gridLayoutManager.itemCount
                val childCount = gridLayoutManager.childCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading() && (childCount + firstVisibleItemPosition + PRELOAD_NUMBER) >= totalItemCount ) {
                    viewModel.loadMore()
                }
            }
        })
    }
}
