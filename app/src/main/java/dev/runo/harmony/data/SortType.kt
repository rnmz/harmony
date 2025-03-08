package dev.runo.harmony.data

enum class SortType {
    ASCENDING,
    DESCENDING
}

fun Boolean.convertToSortType() = if (this) SortType.ASCENDING else SortType.DESCENDING