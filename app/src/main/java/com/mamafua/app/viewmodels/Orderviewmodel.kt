package com.mamafua.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamafua.app.Repo.Repostuff
import com.mamafua.app.Repo.Resource
import com.mamafua.app.models.Endorder
import com.mamafua.app.models.Returnstatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
@HiltViewModel
class Orderviewmodel @Inject constructor(private val repostuff: Repostuff) :
    ViewModel()  {
    fun sendorderz(endorder: Endorder): LiveData<Resource<Returnstatus>> = flow {
        emit(Resource.Loading)
        emit(repostuff.sendordertoserver(endorder))
    }.asLiveData()
}