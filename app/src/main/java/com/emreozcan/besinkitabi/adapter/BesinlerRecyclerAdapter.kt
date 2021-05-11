package com.emreozcan.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.emreozcan.besinkitabi.R
import com.emreozcan.besinkitabi.databinding.CardBesinlerTasarimBinding
import com.emreozcan.besinkitabi.model.Besin
import com.emreozcan.besinkitabi.util.gorselIndir
import com.emreozcan.besinkitabi.util.placeholderCreate
import com.emreozcan.besinkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.card_besinler_tasarim.view.*


class BesinlerRecyclerAdapter(val besinListesi: ArrayList<Besin>): RecyclerView.Adapter<BesinlerRecyclerAdapter.BesinViewHolder>(), BesinTiklandi{
    class BesinViewHolder(var view: CardBesinlerTasarimBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CardBesinlerTasarimBinding>(inflater,R.layout.card_besinler_tasarim,parent,false)
        return BesinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.view.besin = besinListesi[position]
        holder.view.listener = this

    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }
    fun besinListesiGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(view.besinUuid.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)

    }
}