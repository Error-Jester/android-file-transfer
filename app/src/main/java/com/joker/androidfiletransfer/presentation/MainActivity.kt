package com.joker.androidfiletransfer.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.*
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.joker.androidfiletransfer.presentation.ui.composable.FileList
import com.joker.androidfiletransfer.presentation.ui.theme.AndroidFileTransferTheme
import com.joker.backend_server.MyService

class MainActivity: ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFileTransferTheme {
                val isRunning = remember { mutableStateOf(false) }
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Text(MyService.serverLink)
                        if (isRunning.value) { FileList() } else {
                            Button(
                                onClick = {
                                  isRunning.value = requestFilePermission()

                                }) { Text("Hello") }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun requestFilePermission(): Boolean {
        // service Intent
        val serviceIntent = Intent(this, MyService::class.java)

        if (!Environment.isExternalStorageManager()) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("File Access")
                .setMessage("Please Allow File Permission")
                .setPositiveButton("Ok") { _, _ ->
                    startActivity(
                        Intent(
                            ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                        )
                    )
                }
                .setNegativeButton("Close") { dialog, which ->
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                .show()
        }
        startService(serviceIntent)
        return true
    }

}