package com.rahulsonia.dogspictures.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rahulsonia.dogspictures.api.DogApi
import com.rahulsonia.dogspictures.data.DogPagingSource
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogApi: DogApi) {
    fun getImageResult(breed_id: String) =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                maxSize = 100,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { DogPagingSource(dogApi, breed_id) }
        ).flow

    suspend fun getBreedByName(query: String) = dogApi.getBreedByName(query)
}