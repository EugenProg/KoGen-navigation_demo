package com.koGen.kogen_navigation_demo

import kz.evko.navigation.helpers.NavigationResultKey

sealed class NavigationResultValues<T>(override val key: String, override val defaultValue: T) :
    NavigationResultKey<T> {
    data object ShowToast : NavigationResultValues<Boolean>("showToast", false)
}