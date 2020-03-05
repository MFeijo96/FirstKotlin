package com.firstkotlin.mauriciofeijo.firstkotlin.services

import com.firstkotlin.mauriciofeijo.firstkotlin.models.Repository
import com.firstkotlin.mauriciofeijo.firstkotlin.models.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface UserService {
    @GET("users/{user_id}")
    fun getUser(@Path(value = "user_id", encoded = true) url: String) : Observable<User>

    companion object {
        fun create(): UserService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(UserService::class.java)
        }
    }
}