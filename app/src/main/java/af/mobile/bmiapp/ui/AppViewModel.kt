package af.mobile.bmiapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateWeight(newVal: String) {
        _uiState.update { currentState ->
            val error = newVal.toFloatOrNull() == null && newVal.isNotEmpty()

            currentState.copy(
                weight = newVal,
                weightError = error,
                // Clear hasil lama saat input diubah
                isCalculated = false
            )
        }
    }

    fun updateHeight(newVal: String) {
        _uiState.update { currentState ->
            val error = newVal.toFloatOrNull() == null && newVal.isNotEmpty()

            currentState.copy(
                height = newVal,
                heightError = error,
                isCalculated = false
            )
        }
    }

    fun calculateBmi() {
        val current = _uiState.value
        val weight = current.weight.toFloatOrNull()
        val height = current.height.toFloatOrNull()

        // Validasi final
        val isInputValid = weight != null && height != null && weight > 0 && height > 0

        if (isInputValid) {
            // Rumus BMI (Penggabungan data)
            val heightInMeters = height!! / 100f
            val bmi = weight!! / (heightInMeters * heightInMeters)

            // Klasifikasi BMI
            val classification = when {
                bmi < 18.5 -> "Underweight"
                bmi < 24.9 -> "Normal Weight"
                bmi < 29.9 -> "Overweight"
                else -> "Obesity"
            }

            // Update UI State dengan hasil
            _uiState.update { currentState ->
                currentState.copy(
                    bmiValue = String.format("%.2f", bmi),
                    classification = classification,
                    isCalculated = true,
                    weightError = false,
                    heightError = false
                )
            }
        } else {
            // Update state error
            _uiState.update { currentState ->
                currentState.copy(
                    weightError = weight == null || weight <= 0,
                    heightError = height == null || height <= 0,
                    isCalculated = false
                )
            }
        }
    }

    fun reset() {
        _uiState.update { UiState() }
    }
}