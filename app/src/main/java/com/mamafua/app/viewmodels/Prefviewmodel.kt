package com.mamafua.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mamafua.app.Repo.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Prefviewmodel @Inject constructor(private val prefrencemanger: DataStoreRepository) : ViewModel() {

    val readFromDataStore = prefrencemanger.readFromDataStore.asLiveData()

    fun saveToDataStore(email: String,cid:String) = viewModelScope.launch  {
        prefrencemanger.saveToDataStore(email,cid)
    }

    fun logout()=viewModelScope.launch  {
        prefrencemanger.clear()
    }
}