package dev.runo.harmony.common

sealed interface WorkStatus {
    data class Success<T>(val data: T): WorkStatus
    data class Error(val error: ErrorType, val message: String? = null): WorkStatus
    data object Loading: WorkStatus
}