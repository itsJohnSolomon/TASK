package com.jojo.cloudintask.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jojo.cloudintask.paging.UserPagingSource
import com.jojo.cloudintask.retrofit.UsersAPI
import javax.inject.Inject

class UserRepository @Inject constructor(private val quoteAPI: UsersAPI) {

    fun getUser() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { UserPagingSource(quoteAPI) }
    ).liveData
}