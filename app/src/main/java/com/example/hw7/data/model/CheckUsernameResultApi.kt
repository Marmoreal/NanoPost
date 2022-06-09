package com.example.hw7.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class CheckUsernameResponse(
    val result: CheckUsernameResultApi
)

@JsonClass(generateAdapter = false)
enum class CheckUsernameResultApi {
    TooShort,
    TooLong,
    InvalidCharacters,
    Taken,
    Free
}