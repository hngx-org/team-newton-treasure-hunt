package com.teamnewton.treasurehunt.ui.maps

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.teamnewton.treasurehunt.R

@Composable
fun PermissionRationaleDialog(
    modifier: Modifier = Modifier,
    isDialogShown: MutableState<Boolean>,
    activityPermissionResult: ActivityResultLauncher<String>,
    showMapUI: MutableState<Boolean>
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { isDialogShown.value = false },
        confirmButton = {
            Button(
                onClick = {
                    isDialogShown.value = false
                    activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                },
                content = {
                    Text(text = stringResource(id = R.string.location_rationale_button_grant))
                }
            )
        },
        title = {
            Text(text = stringResource(id = R.string.location_rationale_title))
        },
        text = {
            Text(text = stringResource(id = R.string.location_rationale_description))
        },
        dismissButton = {
            Button(
                onClick = {
                    isDialogShown.value = false
                    showMapUI.value = false
                },
                content = {
                    Text(text = stringResource(id = R.string.location_rationale_button_deny))
                }
            )
        }
    )


}