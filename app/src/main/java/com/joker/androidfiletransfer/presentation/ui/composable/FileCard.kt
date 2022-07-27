package com.joker.androidfiletransfer.presentation.ui.composable

import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.androidfiletransfer.R
import com.joker.androidfiletransfer.presentation.ui.theme.AndroidFileTransferTheme
import com.joker.backend_server.data.getFiles
import com.joker.backend_server.models.FileHandler
import java.io.File




@Composable
fun FileCard(file: File, onClick: () -> Unit) {
    val icon: Int = if(file.isDirectory) R.drawable.folder else R.drawable.ic_launcher_foreground
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .clickable(
                onClick = onClick
            )
            .fillMaxWidth()
            .height(60.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            content = {
                Row(
                    content = {
                        Icon(
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(10.dp),
                            contentDescription = if(file.isDirectory) "Folder Icon" else "File Icon",
                            painter = painterResource(icon)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content  = {
                                Text(file.name)
                                Text(file.extension)
                            }
                        )
                    }
                )
                Text("${(file.length() / 1000)}KB")
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun FileList() {
    var filePath by remember { mutableStateOf(Environment.getExternalStorageDirectory()) }
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(getFiles(filePath)) { FileCard(it) {
                if(it.isDirectory) {
                    filePath = File(it.path)
                } else {
                    TODO("DOWNLOAD FILE")
                }
            }
            }
        }

    }
