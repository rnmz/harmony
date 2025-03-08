package dev.runo.harmony.common

sealed interface WorkStatus<out T> {
    data class Success<T>(val data: T): WorkStatus<T>
    data class Error(val error: ErrorType? = null, val message: String? = null): WorkStatus<Nothing>
    data object Loading: WorkStatus<Nothing>
}