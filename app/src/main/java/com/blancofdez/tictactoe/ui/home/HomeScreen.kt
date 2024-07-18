package com.blancofdez.tictactoe.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blancofdez.tictactoe.R
import com.blancofdez.tictactoe.ui.theme.Accent
import com.blancofdez.tictactoe.ui.theme.Background
import com.blancofdez.tictactoe.ui.theme.Orange1
import com.blancofdez.tictactoe.ui.theme.Orange2


@Composable
fun HomeScree(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToGame: (String, String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Header()

        Body(
            onCreateGame = { homeViewModel.onCreateGame(navigateToGame) },
            onJoinGame = { gameId -> homeViewModel.onJoinGame(gameId, navigateToGame) }
        )
    }
}

@Composable
fun Header() {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Firebase", fontSize = 40.sp, color = Orange1, fontWeight = FontWeight.Bold)
        Text(text = "3 en raya", fontSize = 40.sp, color = Orange2, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, Orange1, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            )
        }
    }

}

@Composable
fun Body(onCreateGame: () -> Unit, onJoinGame: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        backgroundColor = Background,
        border = BorderStroke(4.dp, Orange1),
        shape = RoundedCornerShape(12.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {

            var createGame by remember { mutableStateOf(true) }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = Background,
                border = BorderStroke(1.dp, Accent),
                shape = RoundedCornerShape(12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(
                        text = "Crear o unirse a partida",
                        fontSize = 18.sp,
                        color = Accent,
                        fontWeight = FontWeight.Bold,
                    )

                    Switch(
                        checked = createGame,
                        onCheckedChange = { createGame = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Orange2)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            AnimatedContent(targetState = createGame, label = "") {
                when (it) {
                    true -> CreateGame(onCreateGame)
                    false -> JoinGame(onJoinGame)
                }
            }
        }
    }
}


@Composable
fun CreateGame(onCreateGame: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onCreateGame() }, colors = ButtonDefaults.buttonColors(
            backgroundColor = Orange1
        )
    ) {
        Text(text = "Crear partida", color = Accent)
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun JoinGame(onJoinGame: (String) -> Unit) {

    var text by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Accent,
                focusedBorderColor = Orange1,
                unfocusedBorderColor = Orange2,
                cursorColor = Orange2
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onJoinGame(text) },
            enabled = text.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Orange1,
                disabledBackgroundColor = Orange2
            )
        )
        {
            Text(text = "Unirse a partida", color = Accent)
        }
    }
}