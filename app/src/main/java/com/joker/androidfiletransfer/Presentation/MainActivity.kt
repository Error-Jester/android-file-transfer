package com.joker.androidfiletransfer.Presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.joker.androidfiletransfer.Presentation.ui.theme.AndroidFileTransferTheme
import com.joker.backend_server.MyService
import com.joker.backend_server.NetworkUtils
import com.joker.backend_server.getFiles
import java.io.File
import java.security.Permission

class MainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        var intent = Intent(this, MyService::class.java)
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFileTransferTheme {
//                Text("Hello")
                MainPage(intent)
            }
        }
    }


    @Composable
    fun MainPage(intent: Intent) {
        var clicked = remember { mutableStateOf(false)};
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(content = {
                    Text(NetworkUtils.getLocalIpAddress()!!)
                    if(!clicked.value) {
                        Button(
                            content = {
                                Text("Hello 2")
                            },
                            onClick = {
                                when {
                                    ContextCompat.checkSelfPermission(
                                        this@MainActivity,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                                        this@MainActivity,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    ) == PackageManager.PERMISSION_GRANTED -> {
                                        startService(intent)
                                        clicked.value = true
                                    }
                                    else -> {
                                        // You can directly ask for the permission.
                                        // The registered ActivityResultCallback gets the result of this request.
                                        requestPermissions(
                                            arrayOf(
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE
                                            ), 1
                                        )
                                    }
                                }
                            })
                    } else {
                        LazyColumn {

                            items(getFiles()) { file ->
                                FileList(file)
                            }
                        }

                    }
                })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FileList(file: File) {
        Card() {
            Row() {
                Column {
                    Text(file.nameWithoutExtension)
                    Text(file.extension)
                }
                Text((file.length() /1000).toString())
            }
        }
    }

}