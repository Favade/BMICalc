package com.example.bmicalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalc.ui.theme.BMICalcTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BmiCalculatorScreen()
                }
            }
        }
    }
}

//BMI Categories:
//Underweight = <18.5
//Normal weight = 18.5–24.9
//Overweight = 25–29.9
//Obesity = BMI of 30 or greater
private fun calculateBmi(ht: Double, wt: Double): Double {
    val heightTocm = ht * 0.01
    val squrdHt = heightTocm * heightTocm
    val res =  String.format("%.2f", wt / squrdHt).toDouble()
    return res
}

@Composable
fun BmiCalculatorScreen() {
    var height by remember {
        mutableStateOf("")
    }
    var weight by remember {
        mutableStateOf("")
    }

    val ht = height.toDoubleOrNull()?:0.0
    val wt = weight.toDoubleOrNull()?:0.0

    var calc by remember {
        mutableStateOf(0.0)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(
            text = stringResource(id = R.string.bmi)
        )
        EnterMeasurementTextField(
            measurementInput = height,
            measure = {height = it},
            str = R.string.height
        )
        EnterMeasurementTextField(
            measurementInput = weight,
            measure = {weight = it} ,
            str = R.string.weight
        )
        CalculateButton(
            isEnabled = height.isNotEmpty() && weight.isNotEmpty(),
            calculate = {
                calc = calculateBmi(ht,wt)
            }
        )
        Text(
            text = stringResource(id = R.string.result)
        )
        
        Text(text = "$calc")

        when {
            calc < 18.5 -> {
                Text(
                    text = "Underweight",
                    color = Color.Yellow
                )
            }
            calc in 18.5..24.9 -> {
                Text(
                    text = "Normal",
                    color = Color.Green
                )
            }
            calc in 25.5..29.9 -> {
                Text(
                    text = "Overweight",
                    color = Color.Red
                )
            }
            else -> {
                Text(
                    text = "Obese",
                    color = Color.Red
                )
            }
        }
    }

}

@Composable
fun EnterMeasurementTextField(
    measurementInput: String,
    measure: (String) -> Unit,
    str: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = measurementInput ,
        onValueChange = measure,
        label = {
            Text(text = stringResource(id = str))
        }
    )
}

@Composable
fun CalculateButton(
    calculate: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = calculate,
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(id = R.string.calculatebtn)
        )
    }
}

@Preview(showSystemUi = true, showBackground =  true)
@Composable
fun CalculatePreview() {
    BmiCalculatorScreen()
}

