package com.google.books.di

import android.content.Context
import com.google.books.books.BooksActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BooksModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: BooksActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}