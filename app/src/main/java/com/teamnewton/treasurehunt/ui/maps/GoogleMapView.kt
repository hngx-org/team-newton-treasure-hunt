package com.teamnewton.treasurehunt.ui.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState


val juja = LatLng(1.1018, 37.0144)

val defaultCameraPositionState = CameraPosition.fromLatLngZoom(juja, 4f)

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapLoaded: () -> Unit,
) {

    val locationState = rememberMarkerState(
        position = juja
    )
    val mapUISettings by remember {
        mutableStateOf(MapUiSettings(compassEnabled = false))
    }

    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    var showInfoWindow by remember {
        mutableStateOf(false)
    }

    GoogleMap(
        modifier = modifier,
        onMapLoaded = onMapLoaded,
        uiSettings = mapUISettings,
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
        content = {
            // MarkerInfoWindow {}

            Marker(
                state = locationState,
                draggable = true,
                onClick = {
                    if (showInfoWindow) {
                        locationState.showInfoWindow()
                    } else {
                        locationState.hideInfoWindow()
                    }
                    showInfoWindow = !showInfoWindow
                    return@Marker false
                },
                title = "Map Title"
            )
        }
    )

}