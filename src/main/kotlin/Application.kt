import controller.GameController
import view.ConsoleGameView
import view.ConsoleViewErrorHandler

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    val consoleGameView = ConsoleGameView()
    val consoleViewErrorHandler = ConsoleViewErrorHandler(consoleGameView)
    val game = GameController(consoleGameView, consoleViewErrorHandler)
    game.process()
}
