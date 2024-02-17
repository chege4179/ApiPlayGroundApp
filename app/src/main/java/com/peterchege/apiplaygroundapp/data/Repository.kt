package com.peterchege.apiplaygroundapp.data

import com.peterchege.apiplaygroundapp.core.api.ApiService
import com.peterchege.apiplaygroundapp.core.api.NetworkResult
import com.peterchege.apiplaygroundapp.core.api.responses.CharacterResponse
import com.peterchege.apiplaygroundapp.core.api.responses.ErrorObject
import com.peterchege.apiplaygroundapp.core.api.safeApiCall
import javax.inject.Inject

interface Repository {

    suspend fun getCharacterById(id:String):NetworkResult<CharacterResponse,ErrorObject>
}


class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
):Repository{

    override suspend fun getCharacterById(id: String): NetworkResult<CharacterResponse,ErrorObject> {
        return safeApiCall { apiService.getCharacterById(id) }
    }

}