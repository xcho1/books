package com.google.books.repository

import com.google.books.data.Volumes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    companion object {
        const val API_ENDPOINT = "https://www.googleapis.com/books/v1/"
    }

    @GET("volumes")
    fun queryBooks(@Query("q") query: String,
                   @Query("startIndex") startIndex: Int,
                   @Query("maxResults") maxResults: Int): Single<Volumes>
}