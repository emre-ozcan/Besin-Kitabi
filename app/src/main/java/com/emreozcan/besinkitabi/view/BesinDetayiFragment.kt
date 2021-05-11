package com.emreozcan.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.emreozcan.besinkitabi.R
import com.emreozcan.besinkitabi.databinding.FragmentBesinDetayiBinding
import com.emreozcan.besinkitabi.util.gorselIndir
import com.emreozcan.besinkitabi.util.placeholderCreate
import com.emreozcan.besinkitabi.viewmodel.BesinDetayViewModel
import com.emreozcan.besinkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {
    private var besinId =0
    private lateinit var viewModel: BesinDetayViewModel
    private lateinit var databinding: FragmentBesinDetayiBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            besinId  = BesinDetayiFragmentArgs.fromBundle(it!!).besinId
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayViewModel::class.java)
        viewModel.roomVerisiAl(besinId)



        observeLiveData()


    }

    private fun observeLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin->
            besin?.let {
                databinding.besinDetay = it
            }
        })
    }

}