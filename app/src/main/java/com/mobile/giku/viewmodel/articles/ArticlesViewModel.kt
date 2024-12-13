package com.mobile.giku.viewmodel.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.giku.model.remote.articles.ArticleResponse
import com.mobile.giku.viewmodel.state.UIState
import com.mobile.giku.repository.ArticlesRepository
import kotlinx.coroutines.launch

class ArticlesViewModel(private val repository: ArticlesRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>(UIState.Idle)
    val uiState: LiveData<UIState> get() = _uiState

    private val _articles = MutableLiveData<ArticleResponse?>()
    val articles: LiveData<ArticleResponse?> get() = _articles

    fun fetchArticles() {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = repository.fetchArticles()
            result.onSuccess {
                _articles.value = it
                _uiState.value = UIState.Success
            }.onFailure { error ->
                _uiState.value = UIState.Error(error.message ?: "Unknown error occurred")
            }
        }
    }
}