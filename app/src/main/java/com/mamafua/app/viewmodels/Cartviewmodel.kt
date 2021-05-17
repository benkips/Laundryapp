package com.mamafua.app.viewmodels

import androidx.lifecycle.*
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.Repo.CartRepo
import com.mamafua.app.Repo.Repostuff
import com.mamafua.app.Repo.Resource
import com.mamafua.app.models.Categorydata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Cartviewmodel @Inject constructor(private val repostuff: CartRepo) : ViewModel() {

    private val _cartList = MutableLiveData<List<Washitems>>()
    val cartLists: LiveData<List<Washitems>>
        get() = _cartList


    fun additems(tobewasheditems: List<Washitems>) = viewModelScope.launch {
        repostuff.addtocart(tobewasheditems)
    }

     fun getcartcontentz()= viewModelScope.launch {
         _cartList.value=repostuff.getcartstuff()
     }
    fun deletecartstuff(id: Int) = viewModelScope.launch {
        repostuff.deletecartstuff(id)
        _cartList.value=repostuff.getcartstuff()
    }
    fun updatecartstuff(qnty:Int,id: Int) = viewModelScope.launch {
        repostuff.updatecartstuff(qnty,id)
        _cartList.value=repostuff.getcartstuff()
    }





}