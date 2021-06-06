package com.rahulsonia.dogspictures.model

data class ImageListItem(
    val breeds: List<BreedListItem>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)