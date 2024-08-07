package com.blancofdez.tictactoe.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80
)

private val LightColorScheme = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40
)

@Composable
fun TicTacToeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = Typography,
        content = content
    )
}