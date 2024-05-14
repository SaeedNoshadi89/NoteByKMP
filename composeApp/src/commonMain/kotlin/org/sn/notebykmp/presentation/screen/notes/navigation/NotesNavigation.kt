package org.sn.notebykmp.presentation.screen.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sn.notebykmp.presentation.screen.notes.NotesScreen

const val notesRoute = "notes_route"

fun NavController.navigateToNotes( navOptions: NavOptions? = null){
    navigate(notesRoute, navOptions)
}

fun NavGraphBuilder.notesScreenGraph(
    onEditNote: (noteId: String) -> Unit,
    onNavigateToBookmark: () -> Unit,
) {
    composable(
        route = notesRoute
    ) {
        NotesScreen(onNavigateToBookmark = onNavigateToBookmark, onEditNote = {onEditNote(it)})
    }
}