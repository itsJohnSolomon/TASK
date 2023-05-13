package com.jojo.cloudintask.paging
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jojo.cloudintask.models.Result
import com.jojo.cloudintask.retrofit.UsersAPI
import java.lang.Exception

class UserPagingSource(private val quoteAPI: UsersAPI) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            Log.e("position",position.toString());
            val response = quoteAPI.getUsers(position,10)     //result size
            Log.e("position",response.toString());

            return LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position ==200) null else position + 1     //response.totalPages
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



}