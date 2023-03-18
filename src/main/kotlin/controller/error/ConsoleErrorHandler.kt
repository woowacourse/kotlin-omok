package controller.error

import java.lang.Exception

object ConsoleErrorHandler : ErrorHandler {
    override fun log(exception: Exception) {
        println(exception.message)
        println(exception.stackTraceToString())
    }
}
