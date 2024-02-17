package com.peterchege.apiplaygroundapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.apiplaygroundapp.core.api.NetworkResult
import com.peterchege.apiplaygroundapp.core.api.responses.CharacterResponse
import com.peterchege.apiplaygroundapp.core.api.responses.ErrorObject
import com.peterchege.apiplaygroundapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeScreenState(
    val characterId:String? = null,
    val character:CharacterResponse? = null,
    val error:ErrorObject? = null
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: Repository,
):ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onChangeCharacterId(text:String){
        _uiState.update {
            it.copy(characterId = text)
        }

    }

    fun onChangeCharacter(character:CharacterResponse){
        _uiState.update {
            it.copy(character = character)
        }

    }

    fun onChangeError(error:ErrorObject){
        _uiState.update {
            it.copy(error = error)
        }

    }

    fun fetchCharacter(){
        viewModelScope.launch {
            _uiState.value.characterId?.let {
                if (_uiState.value.characterId != null){
                    val response = repository.getCharacterById(id = it)
                    when(response){
                        is NetworkResult.Error -> {
                            _eventFlow.emit("Error : ${response.errorData.error}")
                            onChangeError(response.errorData)

                        }
                        is NetworkResult.Exception -> {
                            _eventFlow.emit("Exception : ${response.e.message}")
                        }
                        is NetworkResult.Success -> {
                            onChangeCharacter(response.data)
                        }
                    }
                }
            }
        }
    }

}