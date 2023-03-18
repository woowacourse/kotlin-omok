package view

import controller.error.ErrorHandler
import java.lang.Exception

class ConsoleViewErrorHandler(private val gameView: GameView) : ErrorHandler {
    override fun log(exception: Exception) {
        exception.message?.let { gameView.renderError("[ERROR] : $it") }
    }
}
