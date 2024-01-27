package com.example.projectkevin.CharacterInf

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectkevin.api.ApiService
import com.example.projectkevin.api.Repository
import com.example.projectkevin.api.RepositoryId
import com.example.projectkevin.api.dataCharacter.ResultCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterInfViewModel (
    private val repository: RepositoryId = RepositoryId(ApiService.instance)
) : ViewModel() {

    private val _characterDetails = mutableStateOf<Result<ResultCharacter>?>(null)
    val characterDetails: Result<ResultCharacter>? by _characterDetails

    private val _currentId = mutableStateOf<Int?>(null)

    fun updateCurrentId(newId: Int) {
        if (_currentId.value != newId) {
            _currentId.value = newId
            loadCharacterDetails(newId)
        }
    }

    private fun loadCharacterDetails(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacterById(characterId).onSuccess { character ->
                _characterDetails.value = Result.success(character)

            }.onFailure {
                // Manejar el error si es necesario
            }
        }
    }
}