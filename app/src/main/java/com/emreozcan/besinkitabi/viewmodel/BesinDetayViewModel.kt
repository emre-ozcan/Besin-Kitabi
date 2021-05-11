package com.emreozcan.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emreozcan.besinkitabi.model.Besin
import com.emreozcan.besinkitabi.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application: Application): BaseViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiAl(uuid : Int){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }
}