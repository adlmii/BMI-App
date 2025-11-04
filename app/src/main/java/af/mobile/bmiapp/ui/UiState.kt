package af.mobile.bmiapp.ui

data class UiState (
    // Input
    val weight: String = "",
    val height: String = "",

    // Validasi/Error
    val weightError: Boolean = false,
    val heightError: Boolean = false,

    // Hasil
    val bmiValue: String = "0.00",
    val classification: String = "Input Data Dulu",
    val isCalculated: Boolean = false
)