package org.sn.notebykmp.presentation.screen.add_note.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.sn.notebykmp.presentation.screen.add_note.AddEditNoteScreen

const val addOrEditNoteRoute = "add_edit_route"
const val idArg = "idArg"

fun NavController.navigateToAddOrEditNote(noteId: String?, navOptions: NavOptions? = null) {
    navigate("$addOrEditNoteRoute/$noteId", navOptions)
}

fun NavGraphBuilder.addEditNoteScreenGraph(
    onShowSnackbar: suspend (String) -> Boolean,
    onBack: () -> Unit,
) {
    composable(
        route = "$addOrEditNoteRoute/{$idArg}",
        arguments = listOf(
            navArgument(idArg) { type = NavType.StringType; nullable = true },
        )
    ) {backStackEntry ->
        AddEditNoteScreen(
            onShowSnackbar = { onShowSnackbar(it) },
            onBack = onBack,
            noteId = backStackEntry.arguments?.getString(idArg)
        )
    }
}