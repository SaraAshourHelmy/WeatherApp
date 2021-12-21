package com.example.weatherapp.utils

import java.math.RoundingMode


fun getApproximateNumber(number: Double) =
    number.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()