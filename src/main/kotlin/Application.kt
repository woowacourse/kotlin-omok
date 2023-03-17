import controller.GameController
import view.ConsoleGameView

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    val game = GameController(ConsoleGameView())
    game.process()
}
