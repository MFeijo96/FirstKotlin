package com.firstkotlin.mauriciofeijo.firstkotlin.services

import com.firstkotlin.mauriciofeijo.firstkotlin.models.Repository
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoriesService {
//        @GET("users/mfeijo96/repos")
//        fun getRepositories(): Observable<List<Repository>>
//
//        @GET("users/mfeijo96/repos")
//        fun getRepositories2(): Observable<List<Repository>>

    @GET("users/{user_id}/repos")
    fun getRepositories(@Path(value = "user_id", encoded = true) url: String) : Observable<List<Repository>>;

    companion object {
        fun create(): RepositoriesService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(RepositoriesService::class.java)
        }
    }
}