package com.mamafua.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamafua.app.Repo.Repostuff
import com.mamafua.app.Repo.Resource
import com.mamafua.app.models.Locationdata
import com.mamafua.app.models.Returnstatus
import com.mamafua.app.models.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class registrationviewmodel  @Inject constructor(private  val repostuff: Repostuff) : ViewModel(){

    fun registerationusers(email: String,pass:String,name:String,phone:String,location:String): LiveData<Resource<Returnstatus>> {
        return flow {
            emit(Resource.Loading)
            emit(repostuff.register(email,pass,name,phone,location))
        }.asLiveData(Dispatchers.IO)
    }
    fun getlocs(loco:String): LiveData<Resource<Locationdata>> {
        return flow {
            emit(Resource.Loading)
            emit(repostuff.locations(loco))
        }.asLiveData(Dispatchers.IO)
    }
}