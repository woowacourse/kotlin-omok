import controller.GameController
import domain.RenjuRule
import domain.RenjuRuleAdapter

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    val game = GameController()
    game.process()
}
