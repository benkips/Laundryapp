package com.mamafua.app.viewmodels

import androidx.lifecycle.*
import com.mamafua.app.Repo.Repostuff
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class Categoryservicesviewmodel @Inject constructor(private val repostuff: Repostuff) :
    ViewModel() {
    private val searchstring = MutableLiveData<String>()
    private val cid = MutableLiveData<String>()

    val combined = MediatorLiveData<Pair<String?, String?>>().apply {
        addSource(searchstring) {
            value = Pair(it, cid.value)
        }
        addSource(cid) {
            value = Pair(searchstring.value,it)
        }
    }
    val result = Transformations.switchMap(combined) { f ->
        val q = f.first
        val c = f.second
        repostuff.getSearchresults(q!!, c!!)
    }

    fun getsearches(query: String, cids: String) {
        searchstring.value = query
        cid.value = cids
    }

}







/* private val searchstring = MutableLiveData<Dataforservice>()
    val result = searchstring.switchMap {f->
        liveData {
            emit(Resource.Loading)
            emit(repostuff.getSearchresults(f.query,f.cid)) }
    }
    fun searchphotos(query:Dataforservice){
        searchstring.value=query
    }*/
