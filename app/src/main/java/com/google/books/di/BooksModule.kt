package com.google.books.di

import androidx.lifecycle.ViewModel
import com.google.books.repository.BooksApiService
import com.google.books.repository.BooksRepository
import com.google.books.repository.BooksRepositoryApi
import com.google.books.books.BooksViewModel
import dagger.*
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
internal abstract class BooksModule {

    @Binds
    abstract fun bindBooksRepository(impl: BooksRepositoryApi): BooksRepository

    @Binds
    abstract fun bindBooksViewModel(implementation: BooksViewModel): ViewModel

    @Binds @IntoMap
    @ClassKey(BooksApiService::class)
    abstract fun lookupBooksApiService(impl: BooksApiService): Any
}
