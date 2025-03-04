package dev.runo.harmony.common

sealed interface ErrorType {
    /*
    System errors: manifest, content resolver, etc
     */
    enum class System : ErrorType {

    }

    /*
    Local errors: empty cursor, data not found
     */
    enum class Local : ErrorType {
        NOT_FOUND
    }
}

