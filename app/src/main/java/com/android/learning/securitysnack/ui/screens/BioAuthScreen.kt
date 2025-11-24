package com.android.learning.securitysnack.ui.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.android.learning.securitysnack.utilities.AppLock

@Composable
fun BioAuthScreen(modifier: Modifier){
    val activity: FragmentActivity = LocalActivity.current as FragmentActivity
    Column(modifier = modifier.padding(5.dp)) {

        Button(onClick = { AppLock.bioMetricAuthAllTime(
            activity,
            onSuccess = {}
        )} ) {
            Text("All time auth")
        }
        Button(onClick = {
            AppLock.bioMetricTimeBased(activity,{})
        }) {
            Text("Time Based auth")
        }
    }



}