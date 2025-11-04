package af.mobile.bmiapp

import af.mobile.bmiapp.ui.AppViewModel
import af.mobile.bmiapp.ui.UiState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import af.mobile.bmiapp.ui.theme.BMIAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMIAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    BmiScreen(Modifier.padding(it))
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )

                }
            }
        }
    }
}

@Composable
fun BmiScreen(modifier: Modifier) {
    val vm: AppViewModel = viewModel()
    val state: UiState by vm.uiState.collectAsState()

    val statusColor = when (state.classification) {
        "Underweight" -> Color(0xFF64B5F6)
        "Normal Weight" -> Color(0xFF81C784)
        "Overweight" -> Color(0xFFFFB74D)
        "Obesity" -> Color(0xFFE57373)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Kalkulator Indeks Massa Tubuh",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))

        // Input Berat Badan
        OutlinedTextField(
            value = state.weight,
            onValueChange = { vm.updateWeight(it) },
            label = { Text("Berat Badan (kg)") },
            isError = state.weightError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (state.weightError) {
            Text(
                "Harap masukkan angka Berat Badan yang valid (> 0)",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(16.dp))

        // Input Tinggi Badan
        OutlinedTextField(
            value = state.height,
            onValueChange = { vm.updateHeight(it) },
            label = { Text("Tinggi Badan (cm)") },
            isError = state.heightError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (state.heightError) {
            Text(
                "Harap masukkan angka Tinggi Badan yang valid (> 0)",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(32.dp))

        // Tombol Hitung
        Button(
            onClick = { vm.calculateBmi() },
            enabled = !state.weightError && !state.heightError && state.weight.isNotEmpty() && state.height.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("HITUNG BMI", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(48.dp))

        // Hasil
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = statusColor,
                contentColor = if (state.isCalculated) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = if (state.isCalculated) 8.dp else 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (state.isCalculated) {
                    Text(
                        text = "STATUS BERAT BADAN ANDA",
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = state.classification.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Nilai BMI: ${state.bmiValue}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Text(
                        text = getBmiAdvice(state.classification),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                } else {
                    Text(
                        text = "Ayo Hitung BMI Anda!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Masukkan Berat Badan (kg) dan Tinggi Badan (cm) di atas, lalu tekan tombol HITUNG BMI.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Tombol Reset
        OutlinedButton(
            onClick = { vm.reset() },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("RESET")
        }
    }
}

@Composable
fun getBmiAdvice(classification: String): String {
    return when (classification) {
        "Underweight" -> "Berat badan Anda kurang. Pertimbangkan untuk menambah nutrisi."
        "Normal Weight" -> "Berat badan Anda ideal! Pertahankan gaya hidup sehat."
        "Overweight" -> "Anda memiliki kelebihan berat badan. Atur pola makan dan olahraga teratur."
        "Obesity" -> "Obesitas. Konsultasikan dengan ahli kesehatan untuk program diet dan olahraga intensif."
        else -> "Masukkan berat dan tinggi badan yang valid untuk mendapatkan status."
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMIAppTheme {
        Greeting("Android")
    }
}