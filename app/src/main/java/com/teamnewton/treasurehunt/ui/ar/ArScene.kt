package com.teamnewton.treasurehunt.ui.ar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.ar.core.Config
import com.teamnewton.treasurehunt.R
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode


@Composable
fun FullScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()){

            val currentModel = remember {
                mutableStateOf("gun")
            }
            ARScreen(model = currentModel.value)
            Menu(modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    currentModel.value = it
                })
        }
    }
}
@Composable
fun ARScreen(model:String) {
    val nodes = remember { mutableStateListOf<ArNode>() }
    val modelNode = remember{
        mutableStateOf<ArNode?>(null)
    }
    val placeModelButton = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()){
        ARScene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            planeRenderer = true,
            onCreate = { arSceneView ->
                // Apply your configuration
                // arSceneView.geospatialEnabled = true
                arSceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED
                arSceneView.planeRenderer.isShadowReceiver = false

                modelNode.value = ArModelNode(arSceneView.engine, PlacementMode.INSTANT).apply {
                    loadModelGlbAsync(
                        glbFileLocation = "models/${model}.glb"
                    ) {

                    }
                    onAnchorChanged = {
                        placeModelButton.value = !isAnchored

                    }
                    onHitResult = { node, hitResult ->
                        placeModelButton.value = node.isTracking
                    }
                }
                modelNode.value?.let { nodes.add(it) }

            },
            onSessionCreate = {
                planeRenderer.isVisible = false
            }
        )


        if (placeModelButton.value) {
            Button(onClick = { modelNode.value?.anchor },
                modifier = Modifier.align(Alignment.Center

                )) {
                Text(text = "Place it")
            }
        }
    }

    LaunchedEffect(key1 = model ){
        modelNode.value?.loadModelGlbAsync(
            glbFileLocation = "models/${model}.glb"
        )
        Log.e("errorLoading", "ERROR LOADING MODEL")
    }
    }



@Composable
fun Menu(modifier: Modifier, onClick: (String) -> Unit){
   val menuItems = listOf(
        "gun", "bag", "ring","sneakers"
    )
   var currentIndex by remember{
        mutableStateOf(0)
    }

    fun updateCurrentIndex(offset :Int){
        currentIndex = (currentIndex +offset + menuItems.size) % menuItems.size
        onClick(menuItems[currentIndex])
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

