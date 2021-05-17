package com.mamafua.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamafua.app.Repo.Repostuff
import com.mamafua.app.Repo.Resource
import com.mamafua.app.models.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class loginviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel(){

    fun loginusers(email: String,pass:String): LiveData<Resource<Users>> {
        return flow {
            emit(Resource.Loading)
            emit(repostuff.login(email,pass))
        }.asLiveData(Dispatchers.IO)
    }
}