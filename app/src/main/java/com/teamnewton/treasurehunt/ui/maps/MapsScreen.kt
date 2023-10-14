package com.teamnewton.treasurehunt.ui.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.teamnewton.treasurehunt.MainActivityViewModel
import com.teamnewton.treasurehunt.PermissionEvent
import com.teamnewton.treasurehunt.ViewState
import com.teamnewton.treasurehunt.app.maps.centerOnLocation
import com.teamnewton.treasurehunt.app.maps.hasLocationPermission
import java.util.Locale


private const val NO_OF_ADDRESSES = 1
@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    context: Context,
    locationViewModel: MainActivityViewModel
) {

    //val locationViewModel = viewModel<MainActivityViewModel>()
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val cityName = remember {
        mutableStateOf("")
    }
    LocalContext.current.getCityName(
        latitude =locationViewModel.mlocation?.latitude ?:0.0,
        longitude = locationViewModel.mlocation?.longitude ?:0.0
    ) { address ->
         cityName.value = address.locality
        Log.i("CityMAPSSCREEN",cityName.value)
    }

    val viewState by locationViewModel.viewState.collectAsState()
    val showDialog = remember { mutableStateOf(true) }


    LaunchedEffect(key1 = !context.hasLocationPermission(), block = {
        permissionState.launchMultiplePermissionRequest()
    })
    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                locationViewModel.processEvent(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            PermissionRationaleDialog(
                onShowDialog = { showDialog.value = !showDialog.value },
                onConfirm = { permissionState.launchMultiplePermissionRequest() },
                )
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                locationViewModel.processEvent(PermissionEvent.Revoked)
            }
        }
    }
    with(viewState) {
        when (this) {
            ViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            ViewState.RevokedPermissions -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("We need permissions to use this app")
                    Button(
                        onClick = {
                            startActivity(context,Intent(Settings.ACTION_LOCALE_SETTINGS), null)
                        },
                        enabled = !context.hasLocationPermission()
                    ) {
                        if (context.hasLocationPermission()) CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            color = Color.White
                        )
                        else Text("Settings")
                    }
                }
            }

            is ViewState.Success -> {
                val currentLoc =
                    LatLng(
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0
                    )
                val cameraState = rememberCameraPositionState()

                LaunchedEffect(key1 = currentLoc) {
                    cameraState.centerOnLocation(currentLoc)
                }

                val isMapLoaded = remember {
                    mutableStateOf(false)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    content = {
                        Column {
                            GoogleMapView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(450.dp),
                                cameraPositionState = cameraState,
                                currentPosition = currentLoc,
                                onMapLoaded = {
                                    isMapLoaded.value = true
                                }
                            )
                            Text(text = "Current Location: ${cityName.value}", fontSize = 18.sp)

                        }
                    }
                )
            }
        }
    }


}


/**
 * get city name using geocoder
 */

fun Context.getCityName(
    longitude: Double,
    latitude: Double,
    onAddressReceived: (Address) -> Unit
) {
    val geoCoder = Geocoder(this, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geoCoder.getFromLocation(latitude, longitude, NO_OF_ADDRESSES) { addresses ->
            if (addresses.isNotEmpty()) {
                onAddressReceived(addresses[0])
            }
        }
    } else {
        val addresses = geoCoder.getFromLocation(latitude, longitude, NO_OF_ADDRESSES)
        if (addresses?.isNotEmpty() == true) {
            onAddressReceived(addresses[0])
        }
    }
}