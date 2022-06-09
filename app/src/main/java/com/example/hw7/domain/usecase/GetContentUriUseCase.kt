package com.example.hw7.domain.usecase

import android.content.ContentResolver
import android.net.Uri
import javax.inject.Inject

class GetContentUriUseCase @Inject constructor(
    private val contentResolver: ContentResolver
) {

    operator fun invoke(uri: Uri): ByteArray {
        return contentResolver.openInputStream(uri)!!.readBytes()
    }
}