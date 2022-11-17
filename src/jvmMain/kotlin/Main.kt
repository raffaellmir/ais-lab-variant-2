import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.math.roundToInt

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 512.dp, height = 768.dp)
    ) {
        Theme {
            Column(
                Modifier.fillMaxSize().padding(horizontal = 44.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val areaOfApart = remember { mutableStateOf("") }
                val isField1NotEmpty = remember { mutableStateOf(areaOfApart.value.isNotBlank()) }
                StandardTextField(
                    state = areaOfApart,
                    isNotEmpty = isField1NotEmpty,
                    placeholder = "64.5",
                    label = "Площадь вашей квартиры (м²)",
                )
                Spacer(Modifier.size(16.dp))

                val areaOfAllApart = remember { mutableStateOf("") }
                val isField2NotEmpty = remember { mutableStateOf(areaOfAllApart.value.isNotBlank()) }
                StandardTextField(
                    state = areaOfAllApart,
                    isNotEmpty = isField2NotEmpty,
                    placeholder = "6544.5",
                    label = "Площадь всех помещений в доме (м²)"
                )
                Spacer(Modifier.size(16.dp))

                val commonAreas = remember { mutableStateOf("") }
                val isField3NotEmpty = remember { mutableStateOf(commonAreas.value.isNotBlank()) }
                StandardTextField(
                    state = commonAreas,
                    isNotEmpty = isField3NotEmpty,
                    placeholder = "320",
                    label = "Площадь общего пользования (м²)"
                )
                Spacer(Modifier.size(16.dp))

                val dataOfHeatingMeter = remember { mutableStateOf("") }
                val isField4NotEmpty = remember { mutableStateOf(dataOfHeatingMeter.value.isNotBlank()) }
                StandardTextField(
                    state = dataOfHeatingMeter,
                    isNotEmpty = isField4NotEmpty,
                    placeholder = "150",
                    label = "Данные счетчика за отопление (Гкал)"
                )
                Spacer(Modifier.size(16.dp))

                val tariff = remember { mutableStateOf("") }
                val isField5NotEmpty = remember { mutableStateOf(tariff.value.isNotBlank()) }
                StandardTextField(
                    state = tariff,
                    isNotEmpty = isField5NotEmpty,
                    placeholder = "1800",
                    label = "Тариф (рубль/Гкл)"
                )
                Spacer(Modifier.size(16.dp))

                val areaWithoutHeatingSystem = remember { mutableStateOf("") }
                val isField6NotEmpty = remember { mutableStateOf(areaWithoutHeatingSystem.value.isNotBlank()) }
                StandardTextField(
                    state = areaWithoutHeatingSystem,
                    isNotEmpty = isField6NotEmpty,
                    placeholder = "0",
                    label = "Площадь помещений без отопления (м²)"
                )
                Spacer(Modifier.size(32.dp))
                val payment = remember { mutableStateOf("") }
                Button(
                    enabled = isField1NotEmpty.value && isField2NotEmpty.value && isField3NotEmpty.value && isField4NotEmpty.value && isField5NotEmpty.value && isField6NotEmpty.value,
                    content = {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = if (payment.value.isNotEmpty()) "Деньги мне плати ${payment.value} ₽" else "Вычислить"
                        )
                    },
                    onClick = {
                        val areaOfApartD = areaOfApart.value.toDouble()
                        val dataOfHeatingMeterD = dataOfHeatingMeter.value.toDouble()
                        val areaWithoutHeatingSystemD = areaWithoutHeatingSystem.value.toDouble()
                        val areaOfAllApartD = areaOfAllApart.value.toDouble()
                        val commonAreasD = commonAreas.value.toDouble()
                        val tariffD = tariff.value.toDouble()

                        val amountThermalEnergy =
                            ((dataOfHeatingMeterD / (areaOfAllApartD + commonAreasD - areaWithoutHeatingSystemD)) * areaOfApartD)
                        val sumVolumesThermalEnergy =
                            (dataOfHeatingMeterD / (areaOfAllApartD + commonAreasD)) * areaOfAllApartD
                        val amountPayment =
                            ((amountThermalEnergy + ((areaOfApartD * (dataOfHeatingMeterD - sumVolumesThermalEnergy)) / areaOfAllApartD)) * tariffD)

                        payment.value = amountPayment.roundToInt().toString()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
