package com.mamafua.app.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mamafua.app.Repo.ApiInterface
import com.mamafua.app.models.Subservicecategories
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
class Servicedatasource(
private val apiInterface: ApiInterface,
private val query: String,
private val cid: String
): PagingSource<Int,Subservicecategories>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Subservicecategories> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiInterface.getserviceforcategories(position, params.loadSize,query,cid)
            val servicez = response.data
            LoadResult.Page(
                data = servicez,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (servicez.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }


}