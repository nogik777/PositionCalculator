package com.rustam.positioncalculator.ui

import androidx.lifecycle.ViewModel
import com.rustam.positioncalculator.data.PositionCalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PositionCalculatorViewModel : ViewModel() {

    private val _uiState =  MutableStateFlow(PositionCalculatorState())
    val uiState: StateFlow<PositionCalculatorState> = _uiState.asStateFlow()

    fun onDepositInputChanged(input: String) {
        _uiState.update { currentState ->
            currentState.copy(depositInput = input)
        }
    }

    fun onPercentInputChanged(input: String) {
        _uiState.update { currentState ->
            currentState.copy(percentInput = input)
        }
    }

    fun onPriceInputChanged(input: String) {
        _uiState.update { currentState ->
            currentState.copy(priceInput = input)
        }
    }

    fun onRatioInputChanged(input: String) {
        _uiState.update { currentState ->
            currentState.copy(ratioInput = input)
        }
    }

    fun onCreditRatioInputChanged(input: String) {
        _uiState.update { currentState ->
            currentState.copy(creditRatioInput = input)
        }
    }

    fun calculatedStopLoss(
        price: String = _uiState.value.priceInput,
        percent: String = _uiState.value.percentInput,
        creditRatio: String = _uiState.value.creditRatioInput
    ) {
        val leverage = if (creditRatio == "") 1.0 else creditRatio.toDoubleOrNull() ?: 1.0
        val stopLoss = (price.toDoubleOrNull() ?: 0.0) * (1 - (percent.toDoubleOrNull() ?: 0.0) / 100 / leverage)
        _uiState.update { currentState ->
            currentState.copy(stopLoss = stopLoss)
        }
    }

    fun calculatedTakeProfit(
        price: String = _uiState.value.priceInput,
        percent: String = _uiState.value.percentInput,
        creditRatio: String = _uiState.value.creditRatioInput,
        ratio: String = _uiState.value.ratioInput
    ) {
        val leverage = if (creditRatio == "") 1.0 else creditRatio.toDoubleOrNull() ?: 1.0
        val takeProfit =
            (price.toDoubleOrNull() ?: 0.0) * (((ratio.toDoubleOrNull() ?: 0.0)
                    * ((percent.toDoubleOrNull() ?: 0.0) / 100) / leverage)) + (price.toDoubleOrNull() ?: 0.0)
        _uiState.update { currentState ->
            currentState.copy(takeProfit = takeProfit)
        }
    }

    fun calculatedRisk(
        deposit: String = _uiState.value.depositInput,
        percent: String = _uiState.value.percentInput,
    ) {
        val risk = (deposit.toDoubleOrNull() ?: 0.0) * (percent.toDoubleOrNull() ?: 0.0) / 100
        _uiState.update { currentState ->
            currentState.copy(risk = risk)
        }
    }

    fun calculatedROI(
        percent: Double = _uiState.value.percentInput.toDoubleOrNull() ?: 0.0,
        ratio: Double = _uiState.value.ratioInput.toDoubleOrNull() ?: 0.0
    ) {
        val roi = percent * ratio
        _uiState.update { currentState ->
            currentState.copy(roi = roi)
        }
    }

}