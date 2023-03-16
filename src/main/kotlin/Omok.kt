import domain.Board
import domain.Stone
import view.InputView
import view.OutputView

fun main() {
    println("오목 게임을 시작합니다.")
    val board = Board()
    var stone: Stone? = null
    while (board.isFinished().not()) {
        OutputView.printBoard(board)
        if (board.isBlackTurn()) {
            print("흑의 차례입니다.")
        } else {
            print("백의 차례입니다.")
        }
        stone?.let { print(" (마지막 돌의 위치: ${it.x + it.y.toString()})") }
        println()
        stone = InputView.readStone()
        board.put(stone)
    }
    OutputView.printBoard(board)
    if (board.isBlackWin()) println("흑의 승리입니다.") else println("백의 승리입니다.")
}
