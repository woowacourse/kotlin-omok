import controller.GameController

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    val game = GameController()
    game.process()
}
