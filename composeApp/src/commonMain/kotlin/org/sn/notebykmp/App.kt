package org.sn.notebykmp

import androidx.compose.runtime.Composable
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication
import org.sn.notebykmp.di.appModule
import org.sn.notebykmp.theme.AppTheme

@Composable
internal fun App() {
    KoinApplication(application = {
        modules(appModule())
        Napier.base(DebugAntilog())
    }) {
        AppTheme {
            NoteByKmpApp()
        }
    }
}