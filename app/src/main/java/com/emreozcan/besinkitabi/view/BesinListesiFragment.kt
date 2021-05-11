package com.emreozcan.besinkitabi.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emreozcan.besinkitabi.R
import com.emreozcan.besinkitabi.adapter.BesinlerRecyclerAdapter
import com.emreozcan.besinkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*

class BesinListesiFragment : Fragment() {

    private lateinit var viewModel: BesinListesiViewModel
    private var besinlerRecyclerAdapter = BesinlerRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var layoutManager = LinearLayoutManager(context)
        besinListRecycler.layoutManager = layoutManager
        besinListRecycler.adapter = besinlerRecyclerAdapter

        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            textViewHata.visibility = View.GONE
            besinListRecycler.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshSwipe()
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler->
            besinler?.let {
                besinListRecycler.visibility = View.VISIBLE
                besinlerRecyclerAdapter.besinListesiGuncelle(besinler)
            }
        })
        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if (it){
                    textViewHata.visibility = View.VISIBLE
                    besinListRecycler.visibility = View.GONE
                }else{
                    textViewHata.visibility = View.GONE
                }
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor->
            yukleniyor?.let {
                if (it){
                    besinListRecycler.visibility = View.GONE
                    textViewHata.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }else{
                    progressBar.visibility = View.GONE
                }
            }
        })

    }
}