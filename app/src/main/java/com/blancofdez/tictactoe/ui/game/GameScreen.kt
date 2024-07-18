package com.blancofdez.tictactoe.ui.game

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blancofdez.tictactoe.ui.home.Header
import com.blancofdez.tictactoe.ui.model.GameModel
import com.blancofdez.tictactoe.ui.model.PlayerType
import com.blancofdez.tictactoe.ui.theme.Accent
import com.blancofdez.tictactoe.ui.theme.Background
import com.blancofdez.tictactoe.ui.theme.BlueLink
import com.blancofdez.tictactoe.ui.theme.Orange1
import com.blancofdez.tictactoe.ui.theme.Orange2

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    gameId: String,
    userId: String,
    owner: Boolean,
    navigateToHome: () -> Unit
) {
    LaunchedEffect(key1 = true) {

        gameViewModel.joinToGame(gameId, userId, owner)

    }
    val game: GameModel? by gameViewModel.game.collectAsState()
    val winner: PlayerType? by gameViewModel.winner.collectAsState()

    if (winner != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background), contentAlignment = Alignment.Center
        ) {


            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                backgroundColor = Background,
                border = BorderStroke(4.dp, Orange1),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {


                    Text(
                        text = "FELICIDADES",
                        fontSize = 32.sp,
                        color = Orange1,
                        fontWeight = FontWeight.Bold
                    )

                    val currentWinner = if (winner == PlayerType.FirstPlayer) {
                        "Jugador 1"
                    } else {
                        "Jugador 2"
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Ha ganado el", fontSize = 22.sp, color = Accent)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = currentWinner,
                        fontSize = 26.sp,
                        color = Orange2,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Header()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp), contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = { navigateToHome() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Orange1,
                                disabledBackgroundColor = Orange2
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Text(text = "Inicio", color = Accent)
                        }
                    }
                }
            }
        }

    } else {

        Board(game, onItemSelected = { position -> gameViewModel.onItemSelected(position) })

    }
}

@Composable
fun Board(game: GameModel?, onItemSelected: (Int) -> Unit) {
    if (game == null) return

    val clipboard: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        val status = if (game.isGameReady) {
            if (game.isMyTurn) {
                "Tu turno"
            } else {
                "Turno rival"
            }
        } else {
            "Esperando por el jugador 2"
        }

        Spacer(modifier = Modifier.width(48.dp))



        Spacer(modifier = Modifier.height(48.dp))

        Row {
            GameItem(game.board[0]) { onItemSelected(0) }
            GameItem(game.board[1]) { onItemSelected(1) }
            GameItem(game.board[2]) { onItemSelected(2) }
        }
        Row {
            GameItem(game.board[3]) { onItemSelected(3) }
            GameItem(game.board[4]) { onItemSelected(4) }
            GameItem(game.board[5]) { onItemSelected(5) }
        }
        Row {
            GameItem(game.board[6]) { onItemSelected(6) }
            GameItem(game.board[7]) { onItemSelected(7) }
            GameItem(game.board[8]) { onItemSelected(8) }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 24.dp)
        ) {

            Text(text = status, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Accent)
            Spacer(modifier = Modifier.width(24.dp))
            if ((game.isGameReady && !game.isMyTurn) || !game.isGameReady) {

                CircularProgressIndicator(
                    Modifier.size(18.dp),
                    color = Orange2,
                    backgroundColor = Accent
                )
            }
        }



        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Haga click para para copiar el identificador de partida y compartirlo con su rival.",
                color = Accent,
                modifier = Modifier
                    .padding(24.dp),
                textAlign = TextAlign.Center

            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        clipboard.setText(AnnotatedString(game.gameId))
                        Toast
                            .makeText(context, "Copiado", Toast.LENGTH_SHORT)
                            .show()
                    },
                backgroundColor = Background,
                border = BorderStroke(1.dp, Accent),
                shape = RoundedCornerShape(12.dp),
            ) {

                Text(
                    text = game.gameId,
                    textAlign = TextAlign.Center,
                    color = BlueLink,
                    modifier = Modifier
                        .padding(24.dp)

                )
            }
        }
    }
}


@Composable
fun GameItem(playerType: PlayerType, onItemSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(88.dp)
            .border(BorderStroke(2.dp, Accent))
            .clickable { onItemSelected() },
        contentAlignment = Alignment.Center
    )
    {

        AnimatedContent(targetState = playerType.symbol, label = "") {

            Text(
                text = it,
                color = if (playerType is PlayerType.FirstPlayer) Orange1 else Orange2,
                fontSize = 48.sp
            )
        }
    }
}



