package controller.error

import java.lang.Exception

interface ErrorHandler {
    fun log(exception: Exception)
}
