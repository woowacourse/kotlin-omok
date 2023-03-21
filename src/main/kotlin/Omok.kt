import domain.Board
import domain.Point
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.RuleAdapter
import domain.rule.ThreeThreeRule
import view.InputView
import view.OutputView

fun main() {
    println("오목 게임을 시작합니다.")
    val board = Board()
    var point: Point? = null
    val blackRuleAdapter = RuleAdapter(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
    while (board.isFinished().not()) {
        OutputView.printBoard(board)
        if (board.isBlackTurn()) {
            print("흑의 차례입니다.")
        }
        if (board.isWhiteTurn()) {
            print("백의 차례입니다.")
        }
        point?.let { print(" (마지막 돌의 위치: ${it.x + it.y.toString()})") }
        println()
        point = putStoneUntilNotOccurErrorAndReturnPoint(board, blackRuleAdapter)
    }
    OutputView.printBoard(board)
    if (board.isBlackWin()) println("흑의 승리입니다.") else println("백의 승리입니다.")
}

private fun putStoneUntilNotOccurErrorAndReturnPoint(board: Board, blackRuleAdapter: RuleAdapter): Point {
    while (true) {
        lateinit var point: Point
        runCatching {
            point = InputView.readPoint()
            board.put(
                point,
                blackRuleAdapter
            )
            return point
        }.onFailure {
            println(it.message)
        }
    }
}
