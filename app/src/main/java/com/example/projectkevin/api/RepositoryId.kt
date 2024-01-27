package com.example.projectkevin.api

import com.example.projectkevin.api.dataCharacter.ResultCharacter
import javax.inject.Inject

class RepositoryId (private val api: ApiService) {

    suspend fun getCharacterById(characterId: Int): Result<ResultCharacter> {
        return try {
            val response = api.getCharacterById(characterId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}