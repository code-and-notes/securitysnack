package com.android.learning.securitysnack.ui.screens

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.android.learning.securitysnack.utilities.AppLock
import com.android.learning.securitysnack.utilities.KeyManager



@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BioAuthScreen(modifier: Modifier){
    val activity: FragmentActivity = LocalActivity.current as FragmentActivity
    var allTimeCounter : Int by remember { mutableIntStateOf(0) }
    var timeBasedCounter:Int by remember { mutableIntStateOf(0) }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Button(onClick = {
            AppLock.bioMetricAuthAllTime(activity,{allTimeCounter+=1})
        }){
            Text("All Time Authenticate")
        }
        Text("Counter for All time authenication $allTimeCounter")
        Button(onClick = {
            AppLock.bioMetricAuthTimeBased(activity,{timeBasedCounter+=1})
        }){
            Text("Time based Authentication")
        }
        Text("Counter for time based $timeBasedCounter")
    }
}
