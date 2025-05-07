package com.leandro.contentproviderexamples.di

import android.content.ContentResolver
import android.content.Context
import com.leandro.contentproviderexamples.data.repository.ContactRepository
import com.leandro.contentproviderexamples.data.repository.ContactRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContactRepository(
        contactRepository: ContactRepository
    ) : ContactRepositoryInterface

    companion object {
        @Provides
        @Singleton
        fun provideContentResolver(@ApplicationContext context: Context) : ContentResolver = context.contentResolver
    }
}