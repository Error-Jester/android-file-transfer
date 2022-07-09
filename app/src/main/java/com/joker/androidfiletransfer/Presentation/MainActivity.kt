package com.joker.androidfiletransfer.Presentation

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.androidfiletransfer.Presentation.ui.theme.AndroidFileTransferTheme
import com.joker.backend_server.MyService
import com.joker.androidfiletransfer.R

class MainActivity : ComponentActivity() {
    companion object {

    }
    var informationIntent = Intent(applicationContext, MyService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MY_ID_ADDRESS", intent.getStringExtra("ip_addr")!!)
        setContent {
            AndroidFileTransferTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().padding(all = 100.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(
                        onClick = {
                            startService(informationIntent)
//                            bindService(Intent(this, MyService::class.java), object : ServiceConnection {
//                                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                                    Toast.makeText(this@MainActivity, "STARTED", Toast.LENGTH_LONG).show()
//                                }
//
//                                override fun onServiceDisconnected(name: ComponentName?) {
//                                    Toast.makeText(this@MainActivity, "STOPPED", Toast.LENGTH_SHORT).show()
//                                }
//
//                            } ,
//                                BIND_AUTO_CREATE )
                            Log.d("Service", "started")
                        }
                    ) {
                        var characterText = remember { mutableStateOf("") }
                        Text("Hello")
                    }
                }
                 }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        stopService(informationIntent)
    }


    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileCard(modifier: Modifier = Modifier, fileName: String, fileSize: Int) {
    Card {
        Row {
            Icon(painter = painterResource(R.drawable.ic_baseline_file_copy_24), contentDescription = "File")
            Column {
                Text(text = fileName)
                Text(text = fileSize.toString(), modifier = modifier)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidFileTransferTheme {

    }
}