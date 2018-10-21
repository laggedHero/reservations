package net.laggedhero.reservations.persistence

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

fun <T> ArgumentCaptor<T>.captureNotNull(): T {
    capture()
    return uninitialized()
}

fun <T> uninitialized(): T = null as T
