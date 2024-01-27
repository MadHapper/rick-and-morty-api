package com.example.projectkevin.api


import com.example.projectkevin.api.dataCharacter.Character
import com.example.projectkevin.api.dataCharacter.ResultCharacter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object{
        private val urlApi:String = "https://rickandmortyapi.com/api/"
        val instance = Retrofit
            .Builder()
            .baseUrl(urlApi)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }
    @GET("character")
    suspend fun getCharacters(@Query("page") pageNammber: Int): Character

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") characterId: Int): ResultCharacter

}






