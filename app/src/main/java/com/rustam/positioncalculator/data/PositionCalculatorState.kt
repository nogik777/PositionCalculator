package com.rustam.positioncalculator.data

data class PositionCalculatorState(
    val depositInput: String = "",
    val percentInput: String = "",
    val priceInput: String = "",
    val ratioInput: String = "",
    val creditRatioInput: String = "",
    val stopLoss: Double = 0.0,
    val takeProfit: Double = 0.0,
    val risk: Double = 0.0,
    val roi: Double = 0.0
)
