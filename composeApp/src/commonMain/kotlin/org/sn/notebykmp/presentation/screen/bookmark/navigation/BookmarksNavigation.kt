package org.sn.notebykmp.presentation.screen.bookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sn.notebykmp.presentation.screen.bookmark.BookmarksScreen

const val bookmarksRoute = "bookmarks_route"

fun NavController.navigateToBookmarks( navOptions: NavOptions? = null){
    navigate(bookmarksRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreenGraph(
) {
    composable(
        route = bookmarksRoute
    ) {
        BookmarksScreen()
    }
}