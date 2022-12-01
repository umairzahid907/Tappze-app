package com.example.tappze.di

import com.example.tappze.data.model.UserDao
import com.example.tappze.data.repository.AuthRepository
import com.example.tappze.data.repository.AuthRepositoryImp
import com.example.tappze.data.repository.UserRepository
import com.example.tappze.data.repository.UserRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore,
        dao: UserDao
    ): AuthRepository = AuthRepositoryImp(auth, database, dao)

    @Provides
    @Singleton
    fun providesUserRepository(
        dao: UserDao,
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): UserRepository = UserRepositoryImp(dao, database, storageReference)
}