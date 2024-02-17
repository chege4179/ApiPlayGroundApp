package com.peterchege.apiplaygroundapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onChangeCharacterId = viewModel::onChangeCharacterId,
        searchCharacter = viewModel::fetchCharacter,
        eventFlow = viewModel.eventFlow
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeScreenState,
    onChangeCharacterId: (String) -> Unit,
    searchCharacter: () -> Unit,
    eventFlow:SharedFlow<String>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            snackbarHostState.showSnackbar(
                message = event
            )
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Rick and Morty App API TEST")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
        ) {
            val text = uiState.characterId ?: ""
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onChangeCharacterId,
                label = {
                    Text(text = "Character ID")
                }
            )
            Button(
                onClick = searchCharacter
            ) {
                Text(text = "Search Character")
            }
            uiState.error?.let {
                Text(text = it.error)
                Text(text = it.errordummy)
            }
            uiState.character?.let {
                Text(text = it.name)
                Text(text = it.type)
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    model = it.image,
                    contentDescription = "Image"
                )
            }
        }

    }

}