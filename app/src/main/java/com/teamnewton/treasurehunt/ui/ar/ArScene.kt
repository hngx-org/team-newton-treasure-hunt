package com.teamnewton.treasurehunt.ui.ar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.teamnewton.treasurehunt.R
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.ArSession
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Rotation

@Composable
fun ARScreen() {
    val nodes = remember { mutableStateListOf<ArNode>() }
    val modalNodes = remember{
        mutableStateOf<ArNode?>(null)
    }
    val placeModelButton = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            planeRenderer = true,
            onCreate = { arSceneView ->
                // Apply your configuration
               // arSceneView.geospatialEnabled = true
                arSceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED
                arSceneView.planeRenderer.isShadowReceiver =false

                modalNodes.value = ArModelNode(arSceneView.engine,PlacementMode.INSTANT).apply {
                    loadModelGlbAsync(
                        glbFileLocation ="models/bag.glb"
                    ){

                    }
                    onAnchorChanged = {
                        placeModelButton.value = !isAnchored

                    }
                    onHitResult = { node, hitResult ->  
                        placeModelButton.value = node.isTracking
                    }
                    modalNodes.value?.let { nodes.add(it) }
                }

            }
        )

      if (placeModelButton.value)  {
            Button(onClick = { modalNodes.value?.anchor }) {
                Text(text = "Place it")
            }
        }
    }

}

@Composable
fun Menu(modifier: Modifier){
   val menuItems = listOf(
        "gun", "bag", "ring","sneakers"
    )
   var currentIndex by remember{
        mutableStateOf(0)
    }

    fun updateCurrentIndex(offset :Int){
        currentIndex = (currentIndex +offset + menuItems.size) % menuItems.size
    }

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround) {

        IconButton(onClick = { updateCurrentIndex(-1) }) {
          Icon(painter = painterResource(id = R.drawable.arrow_left),contentDescription = null)
        }
        Text(text = menuItems[currentIndex]  )

        IconButton(onClick = { updateCurrentIndex(1) }) {
            Icon(painter = painterResource(id = R.drawable.arrow_right),contentDescription = null)
        }

    }
}

