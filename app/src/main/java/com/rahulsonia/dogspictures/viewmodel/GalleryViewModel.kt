package com.rahulsonia.dogspictures.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rahulsonia.dogspictures.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val dogRepository: DogRepository) : ViewModel() {

    fun getImages(breed_id: String) =
        dogRepository.getImageResult(breed_id).cachedIn(viewModelScope)

    suspend fun getBreedsByName(query: String) = dogRepository.getBreedByName(query)
}