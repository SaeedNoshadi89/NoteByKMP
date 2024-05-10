import androidx.compose.ui.window.ComposeUIViewController
import org.sn.notebykmp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
