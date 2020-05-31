package com.yuzuriha.jetpack.tryout.utilities

import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.yuzuriha.jetpack.tryout.BuildConfig
import com.yuzuriha.jetpack.tryout.model.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object Service {
    fun instance(): AppService {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }
        return Retrofit.Builder()
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build()
            .create(AppService::class.java)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

interface AppService {

    @GET("/posts")
    suspend fun getPosts(): ArrayList<PostItem>

    @GET("/comments")
    suspend fun getComments(@Query("postId") postId: Int): ArrayList<CommentsItem>
}