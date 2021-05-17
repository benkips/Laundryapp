package com.mamafua.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamafua.app.Repo.Repostuff
import com.mamafua.app.Repo.Resource
import com.mamafua.app.models.Categorydata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class Categoryviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel() {

    val retrivecategory: LiveData<Resource<Categorydata>> = flow {
            emit(Resource.Loading)
            emit(repostuff.getcategorys())
        }.asLiveData()

}