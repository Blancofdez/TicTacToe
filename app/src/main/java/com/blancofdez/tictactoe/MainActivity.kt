package com.blancofdez.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.blancofdez.tictactoe.ui.core.ContentWrapper
import com.blancofdez.tictactoe.ui.game.GameScreen
import com.blancofdez.tictactoe.ui.home.HomeScree
import com.blancofdez.tictactoe.ui.theme.TicTacToeTheme
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TicTacToeTheme {

                navigationController = rememberNavController()
               ContentWrapper(navigationController)
            }
        }
    }
}