package com.example.hw7.ui.profile

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hw7.domain.model.Post
import com.example.hw7.domain.model.Profile
import com.example.hw7.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfilePostsUseCase: GetProfilePostsUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
    private val deleteUserAuthDataUseCase: DeleteUserAuthDataUseCase
) : ViewModel() {

    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = _profileLiveData

    private val _postsLiveData = MutableLiveData<PagingData<Post>>()
    val postsLiveData: LiveData<PagingData<Post>> = _postsLiveData

    private val _btnSubscribeLiveData = MutableLiveData<Boolean>()
    val btnSubscribeLiveData: LiveData<Boolean> = _btnSubscribeLiveData

    private val _logoutLiveData = MutableLiveData<Any>()
    val logoutLiveData: LiveData<Any> = _logoutLiveData

    fun loadData(profilePostId: String?) {
        viewModelScope.launch {
            if (!profilePostId.isNullOrBlank()) {
                getProfileUseCase(profilePostId).let { profile ->
                    _profileLiveData.value = profile
                }
                getProfilePostsUseCase(profilePostId).cachedIn(viewModelScope).collect {
                    _postsLiveData.value = it
                    _btnSubscribeLiveData.value = _profileLiveData.value?.subscribed
                }
            } else {
                getUserIdUseCase()?.let { userId ->
                    getProfileUseCase(userId).let { profile ->
                        _profileLiveData.value = profile
                    }
                    getProfilePostsUseCase(userId).cachedIn(viewModelScope).collect {
                        _postsLiveData.value = it
                    }
                }
            }
        }
    }

    fun onClickLogout() {
        viewModelScope.launch {
            deleteUserAuthDataUseCase()
            _logoutLiveData.value = Any()
        }
    }

    fun onClickSubscribe() {
        _profileLiveData.value?.let {
            when (it.subscribed) {
                true -> {
                    _btnSubscribeLiveData.value = unsubscribe(it.id)
                }
                false -> {
                    _btnSubscribeLiveData.value = subscribe(it.id)
                }
            }
        }
    }

    private fun subscribe(profileId: String): Boolean {
        viewModelScope.launch {
            subscribeUseCase(profileId)
            _profileLiveData.value = getProfileUseCase.invoke(profileId)
        }
        return true
    }

    private fun unsubscribe(profileId: String): Boolean {
        viewModelScope.launch {
            unsubscribeUseCase(profileId)
            _profileLiveData.value = getProfileUseCase.invoke(profileId)
        }
        return false
    }
}
