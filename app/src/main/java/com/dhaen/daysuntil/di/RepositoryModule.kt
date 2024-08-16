package com.dhaen.daysuntil.di

import com.dhaen.daysuntil.data.CountDownEvent
import com.dhaen.daysuntil.data.repository.Repository
import com.dhaen.daysuntil.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRealm(): Realm {
        val config = RealmConfiguration.create(schema = setOf(CountDownEvent::class))
        return Realm.open(configuration = config)
    }


    @Provides
    @Singleton
    fun provideRepository(
        localRealm: Realm,
    ): Repository {
        return RepositoryImpl(localRealm = localRealm)
    }
}