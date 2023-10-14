package com.teamnewton.treasurehunt.ui.admin.treasures

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamnewton.treasurehunt.R
import com.teamnewton.treasurehunt.app.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    treasure: Game,
    onTreasureClick: (String) -> Unit,
) {
    val onItemClicked = remember {
        {
            onTreasureClick(treasure.documentId)
        }
    }


    ElevatedCard(
        modifier = modifier.fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = onItemClicked,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        content = {
            Box(
                modifier = Modifier.height(200.dp).padding(12.dp),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.treasure_box),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 300f
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomStart,
                        content = {
                            Text(
                                text = treasure.treasureHuntName,
                                style = TextStyle(color = Color.White, fontSize = 16.sp)
                            )
                        }
                    )

                }
            )
        }
    )


}