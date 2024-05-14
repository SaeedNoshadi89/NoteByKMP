@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package org.sn.notebykmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import notebykmp.composeapp.generated.resources.Res
import notebykmp.composeapp.generated.resources.archive_minus
import notebykmp.composeapp.generated.resources.bookmark
import notebykmp.composeapp.generated.resources.note_icon
import notebykmp.composeapp.generated.resources.notes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.sn.notebykmp.presentation.screen.add_note.navigation.addEditNoteScreenGraph
import org.sn.notebykmp.presentation.screen.add_note.navigation.navigateToAddOrEditNote
import org.sn.notebykmp.presentation.screen.bookmark.navigation.bookmarksRoute
import org.sn.notebykmp.presentation.screen.bookmark.navigation.bookmarksScreenGraph
import org.sn.notebykmp.presentation.screen.bookmark.navigation.navigateToBookmarks
import org.sn.notebykmp.presentation.screen.notes.navigation.navigateToNotes
import org.sn.notebykmp.presentation.screen.notes.navigation.notesRoute
import org.sn.notebykmp.presentation.screen.notes.navigation.notesScreenGraph

sealed interface NoteScreensHierarchy {
    val icon: Icon
    val iconTextId: StringResource
    val titleTextId: String

    enum class TopLevelDestination(
        override val icon: Icon,
        override val iconTextId: StringResource,
        override val titleTextId: String,
    ) : NoteScreensHierarchy {
        NOTES(
            icon = Icon.DrawableResourceIcon(Res.drawable.note_icon),
            iconTextId = Res.string.notes,
            titleTextId = notesRoute,
        ),
        BOOKMARKS(
            icon = Icon.DrawableResourceIcon(Res.drawable.archive_minus),
            iconTextId = Res.string.bookmark,
            titleTextId = bookmarksRoute,
        )
    }
}

@Composable
fun NoteByKmpApp(
    appState: NoteAppState = rememberNoteAppState(),
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        bottomBar = {
            BottomAppBarComponent(
                appState = appState
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->

        NavHost(
            navController = appState.navController,
            startDestination = notesRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            notesScreenGraph(
                onNavigateToBookmark = { appState.navController.navigateToBookmarks() },
                onEditNote = { noteId ->
                    appState.navController.navigateToAddOrEditNote(noteId)
                })
            addEditNoteScreenGraph(onShowSnackbar = {
                snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short,
                ) == SnackbarResult.ActionPerformed
            }, onBack = { appState.navController.navigateUp() })
            bookmarksScreenGraph()
        }
    }
}

@Composable
private fun BottomAppBarComponent(
    appState: NoteAppState,
) {
    val navBarVisibility = appState.topLevelDestinations.any {
        appState.currentDestination.isTopLevelDestinationInHierarchy(
            it
        )
    }
    Napier.e(navBarVisibility.toString())
    if (navBarVisibility) {
        NoteBottomAppBar(
            destinations = appState.topLevelDestinations,
            onNavigateToDestination = appState::navigateToTopLevelDestination,
            currentDestination = appState.currentDestination,
            onAdd = { appState.navController.navigateToAddOrEditNote(null) }
        )
    }

}

@Composable
private fun NoteBottomAppBar(
    destinations: List<NoteScreensHierarchy>,
    onNavigateToDestination: (NoteScreensHierarchy) -> Unit,
    currentDestination: NavDestination?,
    onAdd: () -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                val textStyle = if (selected) MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                else MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.outline
                )

                val iconTint =
                    if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { onNavigateToDestination(destination) }) {
                        when (val icon = destination.icon) {
                            is Icon.DrawableResourceIcon -> Icon(
                                painter = painterResource(icon.resource),
                                contentDescription = null,
                                tint = iconTint
                            )
                        }
                    }
                    Text(
                        text = stringResource(destination.iconTextId),
                        style = textStyle
                    )
                }
            }
        },
        floatingActionButton = {
            Button(onClick = { onAdd() }) {
                Icon(Icons.Filled.Add, "Add note")
            }
        }
    )
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: NoteScreensHierarchy): Boolean {
    val destinationName = when (destination) {
        is NoteScreensHierarchy.TopLevelDestination -> destination.name
    }
    return this?.hierarchy?.any {
        it.route?.contains(destinationName, true) == true
    } ?: true
}

@Composable
fun rememberNoteAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): NoteAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        NoteAppState(
            navController,
        )
    }
}

@Stable
class NoteAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    val topLevelDestinations: List<NoteScreensHierarchy.TopLevelDestination> =
        NoteScreensHierarchy.TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: NoteScreensHierarchy) {
        val topLevelNavOptions = navOptions {
            navController.graph.findStartDestination().route?.let {
                popUpTo(it) {
                    saveState = true
                    inclusive = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            NoteScreensHierarchy.TopLevelDestination.NOTES -> {
                navController.navigateToNotes(
                    navOptions = topLevelNavOptions
                )
            }

            NoteScreensHierarchy.TopLevelDestination.BOOKMARKS -> navController.navigateToBookmarks(
                navOptions = topLevelNavOptions
            )
        }
    }
}


sealed class Icon {
    data class DrawableResourceIcon(val resource: DrawableResource) : Icon()
}