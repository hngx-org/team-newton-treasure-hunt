package com.teamnewton.treasurehunt.ui.admin.treasuredetail

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(timeStamp:Timestamp): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(timeStamp.toDate())
}