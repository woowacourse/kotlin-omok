package console

import error.ErrorHandler
import error.OmokError
import view.GameView

class ConsoleViewErrorHandler(private val gameView: GameView) : ErrorHandler {
    override fun log(exception: OmokError) {
        gameView.renderError(
            "[ERROR] : ${exception.message}"
        )
    }
}
