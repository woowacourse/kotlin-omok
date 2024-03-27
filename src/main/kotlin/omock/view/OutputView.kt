package omock.view

import omock.model.board.LocalBoard.boardForm
import omock.model.board.LocalBoard.boardTable
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.stone.Stone

object OutputView {

    fun outputBoardForm() {
        boardForm.forEachIndexed { index, s ->
            if (index == boardForm.size - 1) {
                println(s)
            } else {
                println(s.format(*boardTable[index].toTypedArray()))
            }
        }
    }

    fun outputUserLocation() {
        print("위치를 입력하세요:")
    }

    fun outputSuccessOMock() {
        println("오목~!~!~!~!~!~")
    }

    fun outputUserTurn(player: Player) {
        print("${getStoneName(player)}의 차례입니다.")
    }

    private fun outputPrintLine() {
        println()
    }

    fun outputLastStone(lastStone: Stone?) {
        lastStone?.let { stone ->
            println(stone.toString())
        } ?: outputPrintLine()
    }

    fun outputGameStart() {
        println("오목 게임을 시작합니다.")
    }

    fun outputFailureMessage(throwable: Throwable) {
        println(throwable.message)
    }

    fun getStoneName(player: Player): String {
        return when (player) {
            is BlackPlayer -> "흑"
            is WhitePlayer -> "백"
        }
    }
}
