import domain.OmokGame
import domain.Stone
import view.InputView
import view.OutputView

fun main() {
    println("오목 게임을 시작합니다.")
    val omokGame = OmokGame()
    var stone: Stone? = null
    while (omokGame.isFinished().not()) {
        OutputView.printBoard(omokGame)
        if (omokGame.isBlackTurn()) {
            print("흑의 차례입니다.")
        } else {
            print("백의 차례입니다.")
        }
        stone?.let { print(" (마지막 돌의 위치: ${it.x + it.y.toString()})") }
        println()
        stone = putStoneUntilNotOccurErrorAndReturnStone(omokGame)
    }
    OutputView.printBoard(omokGame)
    if (omokGame.isBlackWin()) println("흑의 승리입니다.") else println("백의 승리입니다.")
}

private fun putStoneUntilNotOccurErrorAndReturnStone(omokGame: OmokGame): Stone {
    while (true) {
        val stone = InputView.readStone()
        runCatching {
            omokGame.put(stone)
            return stone
        }.onFailure {
            println("규칙에 어긋납니다.")
        }
    }
}
