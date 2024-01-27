package com.example.projectkevin.api

import com.example.projectkevin.api.dataCharacter.CharacterList
import com.example.projectkevin.api.dataCharacter.ResultCharacter
import javax.inject.Inject


class Repository ( private val api: ApiService )
    {
    suspend fun getCharacters(pageNumber: Int):Result<List<CharacterList>>
    {

    return try{
        val response = api.getCharacters(pageNumber).results
        val character = response.map { list(it) }
        Result.success(character)
    }catch (e: Exception){
        Result.failure(e)
    }

    }

    private fun list (data : ResultCharacter) : CharacterList {
        return CharacterList(
            name = data.name,
            id = data.id,
            image = data.image
        )
    }
}
