package com.rahulsonia.dogspictures.data

import android.util.Log
import androidx.paging.PagingSource
import com.rahulsonia.dogspictures.api.DogApi
import com.rahulsonia.dogspictures.model.ImageListItem
import retrofit2.HttpException
import java.io.IOException

private const val DOG_STARTING_PAGE_INDEX = 1

class DogPagingSource constructor(private val dogApi: DogApi, private val query: String) :
    PagingSource<Int, ImageListItem>() {
    private val TAG = "DogPagingSource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageListItem> {
        val position = params.key ?: DOG_STARTING_PAGE_INDEX

        return try {
            val imageList = dogApi.getImages(query, position, params.loadSize)

            Log.d(TAG, "load: Success")
            LoadResult.Page(
                data = imageList,
                prevKey = if (position == DOG_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (imageList.isEmpty()) null else position + 1
            )

        } catch (e: IOException) {
            Log.d(TAG, "load: $e")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "load: $e")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.d(TAG, "load: $e")
            LoadResult.Error(e)
        }
    }
}