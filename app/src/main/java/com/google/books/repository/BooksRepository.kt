package com.google.books.repository

import com.google.books.data.Volumes
import io.reactivex.Single

interface BooksRepository {

    fun queryBooks(query: String, startIndex: Int, maxResults: Int): Single<Volumes>
}