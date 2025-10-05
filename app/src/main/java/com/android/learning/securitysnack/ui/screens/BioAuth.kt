package com.android.learning.securitysnack.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.android.learning.securitysnack.utilities.AppLock


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BioAuthHome() {

    var bioAuthScreens : BioAuthScreens by remember { mutableStateOf(BioAuthScreens.None)}
    val activity = LocalActivity.current as FragmentActivity
    val setAllTimeScreen = {
        activity.runOnUiThread {
            bioAuthScreens = BioAuthScreens.AllTimeAuth
        }}

    when(bioAuthScreens){
        BioAuthScreens.None -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This will take to Biometric Auth Screen", fontSize = 20.sp)
                Button({
                    AppLock.promptForAuthentication(activity, onSuccess = setAllTimeScreen)
                }) {Text("Go to All time auth") }
                Button({
                    AppLock.promptForAuthenticationTimeBased(activity, onSuccess = {
                        bioAuthScreens = BioAuthScreens.TimeBasedAuth
                    })
                }) {Text("Go to Time based auth") }
            }
        }
        BioAuthScreens.AllTimeAuth -> AllTimeAuth()
        BioAuthScreens.TimeBasedAuth -> TimeBasedAuth()
    }
    BackHandler {
        if(bioAuthScreens != BioAuthScreens.None){
            bioAuthScreens = BioAuthScreens.None
        } else {
            activity.finish()
        }
    }



}

@Composable
fun AllTimeAuth(){
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("All Time Auth Screen", fontSize = 20.sp)
        Text("This screen is accessible only after biometric authentication", fontSize = 18.sp)
    }

}

@Composable
fun TimeBasedAuth(){
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("Time Based Auth Screen", fontSize = 20.sp)
        Text("This screen is accessible only after biometric authentication but valid for 5 sec", fontSize = 18.sp)
    }

}

sealed class BioAuthScreens {
    object AllTimeAuth: BioAuthScreens()
    object TimeBasedAuth: BioAuthScreens()

    object None: BioAuthScreens()
}