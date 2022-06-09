package com.example.hw7.ui.image


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.usecase.DeleteImageUseCase
import com.example.hw7.domain.usecase.GetImageUseCase
import com.example.hw7.domain.usecase.GetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {

    private val _imageLiveData = MutableLiveData<Image>()
    val imageLiveData: LiveData<Image> = _imageLiveData

    private val _navigateLiveData = MutableLiveData<Any>()
    val navigateLiveData: LiveData<Any> = _navigateLiveData

    private val _showMenuLiveData = MutableLiveData<Any>()
    val showMenuLiveData: LiveData<Any> = _showMenuLiveData

    fun loadImage(imageId: String) {
        viewModelScope.launch {
            getImageUseCase(imageId).let { image ->
                _imageLiveData.value = image

                if (getUserIdUseCase() == image.owner.id) {
                    _showMenuLiveData.value = Any()
                }
            }
        }
    }

    fun deleteImage(imageId: String) {
        viewModelScope.launch {
            deleteImageUseCase(imageId)
            _navigateLiveData.value = Any()
        }
    }
}