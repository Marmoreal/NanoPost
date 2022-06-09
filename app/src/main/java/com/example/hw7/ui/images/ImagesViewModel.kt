package com.example.hw7.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.usecase.GetProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getProfileImagesUseCase: GetProfileImagesUseCase
) : ViewModel() {

    private val _imagesLiveData = MutableLiveData<PagingData<Image>>()
    val imagesLiveData: LiveData<PagingData<Image>> = _imagesLiveData

    fun loadData(profileId: String) {
        viewModelScope.launch {
            getProfileImagesUseCase(profileId).collect {
                _imagesLiveData.value = it
            }
        }
    }
}