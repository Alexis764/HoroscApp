package com.example.horoscapp.ui.horoscope

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.horoscapp.databinding.FragmentHoroscopeBinding
import com.example.horoscapp.domain.model.HoroscopeInfo
import com.example.horoscapp.domain.model.HoroscopeInfo.*
import com.example.horoscapp.domain.model.HoroscopeModel
import com.example.horoscapp.ui.horoscope.adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentHoroscopeBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val horoscopeViewModel by viewModels<HoroscopeViewModel>()

    //RecyclerView
    private lateinit var horoscopeAdapter: HoroscopeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {
        initList()
        initUiState()
    }


    private fun initList() {
        horoscopeAdapter = HoroscopeAdapter(onItemSelected = { horoscopeInfo ->
            navHoroscopeDetail(horoscopeInfo)
        })

        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = horoscopeAdapter
        }
    }

    private fun navHoroscopeDetail(horoscopeInfo: HoroscopeInfo) {
        val horoscopeType = when(horoscopeInfo){
            Aquarius -> HoroscopeModel.Aquarius
            Aries -> HoroscopeModel.Aries
            Cancer -> HoroscopeModel.Cancer
            Capricorn -> HoroscopeModel.Capricorn
            Geminis -> HoroscopeModel.Geminis
            Leo -> HoroscopeModel.Leo
            Libra -> HoroscopeModel.Libra
            Pisces -> HoroscopeModel.Pisces
            Sagittarius -> HoroscopeModel.Sagittarius
            Scorpio -> HoroscopeModel.Scorpio
            Taurus -> HoroscopeModel.Taurus
            Virgo -> HoroscopeModel.Virgo
        }

        findNavController().navigate(
            HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeDetailActivity(horoscopeType)
        )
    }


    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeViewModel.horoscope.collect {
                    horoscopeAdapter.updateHoroscopeList(it)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoroscopeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}