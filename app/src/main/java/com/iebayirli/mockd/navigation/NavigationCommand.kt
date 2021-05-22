package com.iebayirli.mockd.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val navDirections: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}
