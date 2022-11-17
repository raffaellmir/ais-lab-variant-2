import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Immutable
data class Colors(
    val tertiary: Color,
    val onTertiary: Color
)

val LocalColors = staticCompositionLocalOf {
    Colors(
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified
    )
}

@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    val colors = Colors(
        tertiary = Color(0xFFA8EFF0),
        onTertiary = Color(0xFF002021)
    )
    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(content = content)
    }
}

@Composable
fun StandardTextField(
    isNotEmpty: MutableState<Boolean>,
    state: MutableState<String>,
    placeholder: String,
    label: String,
) {
    val maxLength = 6

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value,
        onValueChange = { text ->
            if (text.length <= maxLength && text.all { it.isDigit() || it == '.' }) {
                if (text.isNotBlank()) isNotEmpty.value = true
                state.value = text.filter { it.isDigit() || it == '.' }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        singleLine = true,
        trailingIcon = { Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = "Localized description") },
    )
}