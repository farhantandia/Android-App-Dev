package com.example.githubuserapp.parsingAPI

import com.example.githubuserapp.model.DetailUserModel
import com.example.githubuserapp.model.FollowerModel
import com.example.githubuserapp.model.FollowingModel
import com.example.githubuserapp.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 2f94bde6f7c7fd89355467bc3eaa7d96f3808360")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    @Headers("Authorization: token 2f94bde6f7c7fd89355467bc3eaa7d96f3808360")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserModel>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 2f94bde6f7c7fd89355467bc3eaa7d96f3808360")
    fun  //<list> soalnya modelnya dibungkus array karena data ne banyak
            getFollowerUser(
        @Path("username") username: String
    ): Call<List<FollowerModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 2f94bde6f7c7fd89355467bc3eaa7d96f3808360")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<FollowingModel>>

}