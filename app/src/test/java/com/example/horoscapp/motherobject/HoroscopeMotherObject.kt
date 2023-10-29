package com.example.horoscapp.motherobject

import com.example.horoscapp.data.network.response.PredictionResponse
import com.example.horoscapp.domain.model.HoroscopeInfo.*

object HoroscopeMotherObject {

    val anyResponse = PredictionResponse("01/01/01", "Prediction", "taurus")

    val horoscopeInfoList = listOf(
        Aries,
        Taurus,
        Geminis,
        Cancer,
        Leo,
        Virgo,
        Libra,
        Scorpio,
        Sagittarius,
        Capricorn,
        Aquarius,
        Pisces
    )

}