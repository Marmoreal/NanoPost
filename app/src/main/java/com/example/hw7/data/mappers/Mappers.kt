package com.example.hw7.data.mappers

import com.example.hw7.data.model.*
import com.example.hw7.domain.model.*

fun ApiPost.toPost(): Post {
    return Post(
        id = this.id,
        text = this.text,
        owner = this.owner.toOwner(),
        dateCreated = this.dateCreated,
        images = images.map {
            it.toImage()
        },
        likes = this.likes.toLike(),
    )
}

fun ApiOwner.toOwner(): Owner {
    return Owner(
        id = this.id,
        username = this.username,
        displayName = this.displayName,
        avatarUrl = this.avatarUrl,
        subscribed = this.subscribed
    )
}

fun ApiImageSize.toImageSize(): ImageSize {
    return ImageSize(
        width = this.width,
        height = this.height,
        url = this.url
    )
}

fun ApiImage.toImage(): Image {
    return Image(
        id = this.id,
        owner = this.owner.toOwner(),
        dateCreated = this.dateCreated,
        sizes = this.sizes.map {
            it.toImageSize()
        }
    )
}


fun ApiProfile.toProfile(): Profile {
    return Profile(
        id = this.id,
        username = this.username,
        displayName = this.displayName,
        bio = this.bio,
        avatarId = this.avatarId,
        avatarSmall = this.avatarSmall,
        avatarLarge = this.avatarLarge,
        subscribed = this.subscribed,
        subscribersCount = this.subscribersCount,
        postsCount = this.postsCount,
        imagesCount = this.imagesCount,
        images = images.map {
            it.toImage()
        },
    )
}

fun ApiLike.toLike(): Like {
    return Like(
        liked = this.liked,
        likesCount = this.likesCount
    )
}

