@file:Suppress("DEPRECATION")

package com.example.horoscapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.horoscapp.R
import com.example.horoscapp.databinding.ActivityHoroscopeDetailBinding
import com.example.horoscapp.domain.model.HoroscopeModel.Aquarius
import com.example.horoscapp.domain.model.HoroscopeModel.Aries
import com.example.horoscapp.domain.model.HoroscopeModel.Cancer
import com.example.horoscapp.domain.model.HoroscopeModel.Capricorn
import com.example.horoscapp.domain.model.HoroscopeModel.Geminis
import com.example.horoscapp.domain.model.HoroscopeModel.Leo
import com.example.horoscapp.domain.model.HoroscopeModel.Libra
import com.example.horoscapp.domain.model.HoroscopeModel.Pisces
import com.example.horoscapp.domain.model.HoroscopeModel.Sagittarius
import com.example.horoscapp.domain.model.HoroscopeModel.Scorpio
import com.example.horoscapp.domain.model.HoroscopeModel.Taurus
import com.example.horoscapp.domain.model.HoroscopeModel.Virgo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeDetailActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityHoroscopeDetailBinding

    //ViewModel
    private val horoscopeDetailViewModel by viewModels<HoroscopeDetailViewModel>()

    //Safe arguments
    private val args by navArgs<HoroscopeDetailActivityArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoroscopeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        horoscopeDetailViewModel.getHoroscopePrediction(args.horoscopeType)

    }


    private fun initUi() {
        initListeners()
        initUiState()
    }


    private fun initListeners() {
        binding.ivArrowBack.setOnClickListener { onBackPressed() }
    }


    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                horoscopeDetailViewModel.state.collect{ horoscopeDetailState ->
                    when(horoscopeDetailState){
                        HoroscopeDetailState.Loading -> loadingState()
                        is HoroscopeDetailState.Error -> errorState()
                        is HoroscopeDetailState.Success -> successState(horoscopeDetailState)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressDetail.visibility = View.VISIBLE
    }

    private fun errorState() {
        binding.progressDetail.visibility = View.GONE
    }

    private fun successState(horoscopeDetailState: HoroscopeDetailState.Success) {
        binding.progressDetail.visibility = View.GONE
        binding.tvHoroscopeName.text = horoscopeDetailState.sign
        binding.tvHoroscopeDescription.text = horoscopeDetailState.prediction

        val horoscopeImage = when(horoscopeDetailState.horoscopeModel) {
            Aries -> R.drawable.detail_aries
            Taurus -> R.drawable.detail_taurus
            Geminis -> R.drawable.detail_gemini
            Cancer -> R.drawable.detail_cancer
            Leo -> R.drawable.detail_leo
            Virgo -> R.drawable.detail_virgo
            Libra -> R.drawable.detail_libra
            Scorpio -> R.drawable.detail_scorpio
            Sagittarius -> R.drawable.detail_sagittarius
            Capricorn -> R.drawable.detail_capricorn
            Aquarius -> R.drawable.detail_aquarius
            Pisces -> R.drawable.detail_pisces
        }

        binding.ivHoroscopeDetail.setImageResource(horoscopeImage)
    }


}