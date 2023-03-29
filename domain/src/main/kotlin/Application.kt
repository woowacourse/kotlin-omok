import console.ConsoleGameView
import console.ConsoleViewErrorHandler
import controller.GameController

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    val consoleGameView = ConsoleGameView()
    val consoleViewErrorHandler = ConsoleViewErrorHandler(consoleGameView)
    val game = GameController(consoleGameView, consoleViewErrorHandler)
    game.process()
}
