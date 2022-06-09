package com.example.hw7.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw7.domain.model.Post
import com.example.hw7.domain.usecase.DeletePostUseCase
import com.example.hw7.domain.usecase.GetPostUseCase
import com.example.hw7.domain.usecase.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {

    private val _postLiveData = MutableLiveData<Post>()
    val postLiveData: LiveData<Post> = _postLiveData

    private val _navigateLiveData = MutableLiveData<Any>()
    val navigateLiveData: LiveData<Any> = _navigateLiveData

    private val _showMenuLiveData = MutableLiveData<Any>()
    val showMenuLiveData: LiveData<Any> = _showMenuLiveData

    fun loadPost(postId: String) {
        viewModelScope.launch {
            val post = getPostUseCase(postId)
            _postLiveData.value = post

            if (getUserIdUseCase() == post.owner.id) {
                _showMenuLiveData.value = Any()
            }
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            deletePostUseCase(postId)
            _navigateLiveData.value = Any()
        }
    }
}