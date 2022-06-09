package com.example.hw7.ui.create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CreatePostViewModel @Inject constructor(
) : ViewModel() {

    private val _imagesUriLiveData = MutableLiveData<ArrayList<Uri>>()
    val imagesUriLiveData: LiveData<ArrayList<Uri>> = _imagesUriLiveData

    private val _createPostLiveData = MutableLiveData<Pair<String?, ArrayList<Uri>?>>()
    val createPostLiveData: LiveData<Pair<String?, ArrayList<Uri>?>> = _createPostLiveData

    fun addToList(uri: Uri) {
        val list = _imagesUriLiveData.value.orEmpty()
        _imagesUriLiveData.value = ArrayList(list).apply {
            add(uri)
        }
    }

    fun deleteFromList(uri: Uri) {
        val list = _imagesUriLiveData.value.orEmpty()
        _imagesUriLiveData.value = ArrayList(list).apply {
            remove(uri)
        }
    }

    fun onClickCreatePost(text: String?) {
        _createPostLiveData.value = Pair(text, _imagesUriLiveData.value)
    }
}