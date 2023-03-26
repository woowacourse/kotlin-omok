import domain.Board
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.Referee
import domain.rule.ThreeThreeRule
import domain.stone.Point
import view.InputView
import view.OutputView

fun main() {
    println("오목 게임을 시작합니다.")
    val board = Board()
    var point: Point? = null
    val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))
    while (board.isFinished().not()) {
        OutputView.printBoard(board)
        if (board.isBlackTurn()) {
            print("흑의 차례입니다.")
        }
        if (board.isWhiteTurn()) {
            print("백의 차례입니다.")
        }
        point?.let { OutputView.printLastPoint(it) }
        println()
        point = putStoneUntilNotOccurErrorAndReturnPoint(board, blackReferee)
    }
    OutputView.printBoard(board)
    if (board.isBlackWin()) println("흑의 승리입니다.") else println("백의 승리입니다.")
}

private fun putStoneUntilNotOccurErrorAndReturnPoint(
    board: Board,
    blackReferee: Referee
): Point {
    while (true) {
        runCatching {
            val point = InputView.readPoint()
            board.put(
                point,
                blackReferee
            )
            return point
        }.onFailure {
            println(it.message)
        }
    }
}
