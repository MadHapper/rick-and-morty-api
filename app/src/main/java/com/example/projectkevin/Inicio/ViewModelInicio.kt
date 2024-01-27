package com.example.projectkevin.Inicio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectkevin.api.ApiService
import com.example.projectkevin.api.Repository
import com.example.projectkevin.api.dataCharacter.CharacterList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class viewModelInicio (
    private val repository: Repository = Repository(ApiService.instance)
): ViewModel() {

    private val _characters = mutableStateOf<List<CharacterList>>(emptyList())
    val characters: List<CharacterList> by _characters

    private val _currentPage = mutableStateOf(1)
    val currentPage: Int by _currentPage

    init {
        loadCharacters(currentPage)
    }

    fun loadCharacters(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters(page).onSuccess { charactersList ->
                _characters.value = charactersList
                _currentPage.value = page
            }.onFailure {
                // Manejar el error si es necesario
            }
        }
    }
}