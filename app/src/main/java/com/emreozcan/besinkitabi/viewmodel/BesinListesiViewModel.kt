package com.emreozcan.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emreozcan.besinkitabi.model.Besin
import com.emreozcan.besinkitabi.service.BesinAPIServis
import com.emreozcan.besinkitabi.service.BesinDatabase
import com.emreozcan.besinkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application){
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinAPIServis = BesinAPIServis()
    private val disposable = CompositeDisposable()

    private var ozelSharedPreferences =  OzelSharedPreferences(getApplication())
    private var guncellemeZamani = 10*60*1000*1000*1000L


    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        println(kaydedilmeZamani)
        if (kaydedilmeZamani!=null && kaydedilmeZamani!=0L&&System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            verileriSqlittanAl()
        }else{
            verileriInternettenAl()
        }

    }
    fun refreshSwipe(){
        verileriInternettenAl()
    }

    private fun verileriSqlittanAl() {
        besinYukleniyor.value = true
        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAll()
            besinleriGoster(besinListesi)
        }
    }

    private fun verileriInternettenAl() {
        besinYukleniyor.value = true

        disposable.add(
            besinAPIServis.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)
                    }

                    override fun onError(e: Throwable) {
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
    private fun besinleriGoster(besinlerListesi: List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false

    }
    private fun sqliteSakla(besinListesi: List<Besin>){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()

            val uuidList = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidList[i].toInt()
                i++
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }

}