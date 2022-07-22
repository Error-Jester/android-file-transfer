import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.joker.androidfiletransfer.Presentation.ui.theme.AndroidFileTransferTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            AndroidFileTransferTheme {
                FileCard()
            }
        }
    }



}