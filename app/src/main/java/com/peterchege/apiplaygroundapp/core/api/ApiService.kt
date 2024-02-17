package com.peterchege.apiplaygroundapp.core.api

import com.peterchege.apiplaygroundapp.core.api.responses.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id") id:String):Response<CharacterResponse>

}