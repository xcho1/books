package com.google.books.repository

import javax.inject.Inject

class BooksRepositoryApi @Inject constructor() : BooksRepository {

    @Inject lateinit var booksApiService: BooksApiService

    override fun queryBooks(query: String, startIndex: Int, maxResults: Int) =
       booksApiService.queryBooks(query, startIndex, maxResults)
}