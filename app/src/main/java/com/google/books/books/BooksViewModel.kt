package com.google.books.books

import androidx.lifecycle.*
import com.google.books.repository.BooksRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import javax.inject.Inject

inline fun <reified T : ViewModel> ViewModelStoreOwner.injectViewModel(
    crossinline factory: () -> ViewModelProvider.Factory)
        = lazy { ViewModelProvider(this, factory.invoke())[T::class.java] }

class BooksViewModel @Inject constructor(private val booksRepository: BooksRepository) : ViewModel(), DefaultLifecycleObserver {

    @Suppress("UNCHECKED_CAST")
    internal class Factory @Inject constructor(
        private val booksRepository: BooksRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooksViewModel(booksRepository) as T
        }
    }

    companion object {
        private const val INITIAL_AMOUNT_OF_ITEMS = 9
        private const val ADDITIONAL_AMOUNT_OF_ITEMS = 9
    }

    enum class Result {
        SUCCESS,
        ERROR_NETWORK,
        ERROR_UNKNOWN
    }

    private var startIndex = 0
    private var maxResults = INITIAL_AMOUNT_OF_ITEMS
    private var queryText = ""
    private var isLoading = false
    private var isNewQuery = true

    private val disposable = CompositeDisposable()

    val booksAdapter = BooksAdapter()

    val result = MutableLiveData<Result>()

    override fun onStop(owner: LifecycleOwner) = disposable.clear()

    fun isLoading() = isLoading

    fun query(queryText: String) {
        isLoading = true
        isNewQuery = queryText != this.queryText

        if(isNewQuery) {
            this.queryText = queryText
            startIndex = 0
            maxResults = INITIAL_AMOUNT_OF_ITEMS
        }

        disposable.add(booksRepository.queryBooks(queryText, startIndex, maxResults)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { isLoading = false }
            .subscribe({ if (startIndex < it.totalItems) {
                booksAdapter.updateItems(it.items, isNewQuery)
                result.postValue(Result.SUCCESS)
            } }, { handleErrorResponse(it) }
        ))
    }

    fun loadMore() {
        startIndex += maxResults
        maxResults = ADDITIONAL_AMOUNT_OF_ITEMS
        query(queryText)
    }

    private fun handleErrorResponse(t: Throwable) {
        when (t) {
            is UnknownHostException -> result.postValue(Result.ERROR_NETWORK)
            else -> result.postValue(Result.ERROR_UNKNOWN)
        }
    }
}