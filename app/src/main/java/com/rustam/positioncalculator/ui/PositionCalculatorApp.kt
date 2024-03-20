package com.rustam.positioncalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rustam.positioncalculator.R
import com.rustam.positioncalculator.ui.theme.PositionCalculatorTheme

@Composable
fun PositionCalculatorApp(
    positionCalculatorViewModel: PositionCalculatorViewModel = viewModel()
) {
    val uiState by positionCalculatorViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .safeDrawingPadding()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                EditNumberField(
                    label = R.string.deposit,
                    value = uiState.depositInput,
                    onValueChange = { positionCalculatorViewModel.onDepositInputChanged(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardDone = { },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                EditNumberField(
                    label = R.string.percent,
                    value = uiState.percentInput,
                    onValueChange = {
                        positionCalculatorViewModel.onPercentInputChanged(it)
                        if (uiState.depositInput.isNotEmpty()) positionCalculatorViewModel.calculatedRisk()
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardDone = { },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                EditNumberField(
                    label = R.string.price,
                    value = uiState.priceInput,
                    onValueChange = {
                        positionCalculatorViewModel.onPriceInputChanged(it)
                        if (uiState.priceInput.isNotEmpty() && uiState.percentInput.isNotEmpty())
                            positionCalculatorViewModel.calculatedStopLoss()
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardDone = { },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                EditNumberField(
                    label = R.string.ratio,
                    value = uiState.ratioInput,
                    onValueChange = {
                        positionCalculatorViewModel.onRatioInputChanged(it)
                        if (uiState.priceInput.isNotEmpty() && uiState.percentInput.isNotEmpty() && uiState.ratioInput.isNotEmpty())
                            positionCalculatorViewModel.calculatedTakeProfit()

                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardDone = {},
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                EditNumberField(
                    label = R.string.leverage,
                    value = uiState.creditRatioInput,
                    onValueChange = { positionCalculatorViewModel.onCreditRatioInputChanged(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    onKeyboardDone = {
                        positionCalculatorViewModel.calculatedTakeProfit()
                        positionCalculatorViewModel.calculatedStopLoss()
                        positionCalculatorViewModel.calculatedROI()
                        positionCalculatorViewModel.calculatedRisk()
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        positionCalculatorViewModel.calculatedTakeProfit()
                        positionCalculatorViewModel.calculatedStopLoss()
                        positionCalculatorViewModel.calculatedROI()
                        positionCalculatorViewModel.calculatedRisk()
                    }
                ){
                    Text(
                        text = stringResource(R.string.result),
                        fontSize = 16.sp
                    )
                }
                ResultStatus(
                    stopLoss = uiState.stopLoss,
                    takeProfit = uiState.takeProfit,
                    roi = uiState.roi,
                    risk = uiState.risk
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PositionCalculatorTheme {
        PositionCalculatorApp()
    }
}
