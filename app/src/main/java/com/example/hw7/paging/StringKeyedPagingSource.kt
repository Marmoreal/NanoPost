package com.example.hw7.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hw7.data.PagedDataResponse
import com.example.hw7.data.model.ApiPost
import com.example.hw7.domain.NanoPostApiService
import java.lang.Exception
import java.util.concurrent.CancellationException

class StringKeyedPagingSource<T : Any>(
    private val callback: suspend ((LoadParams<String>) -> PagedDataResponse<T>)
) : PagingSource<String, T>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, T> = try {
        val response = callback.invoke(params)
        LoadResult.Page(
            data = response.items,
            prevKey = null,
            nextKey = response.offset.takeIf { response.count >= params.loadSize }
        )

    } catch (e: CancellationException) {
        throw e

    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<String, T>): String? = null
}