package omock.view

import omock.model.board.LocalBoard.boardForm
import omock.model.board.LocalBoard.boardTable
import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.stone.Stone

object OutputView {
    private const val REQUIRE_LOCATION_MESSAGE = "위치를 입력하세요:"
    private const val OUTPUT_SUCCESS_MESSAGE = "오목~!~!~!~!~!~"
    private const val OUTPUT_USER_TURN_MESSAGE = "%s의 차례입니다."
    private const val OUTPUT_GAME_START_MESSAGE = "오목 게임을 시작합니다."
    private const val BLACK_STONE_NAME = "흑"
    private const val WHITE_STONE_NAME = "백"

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
        print(REQUIRE_LOCATION_MESSAGE)
    }

    fun outputSuccessOMock() {
        println(OUTPUT_SUCCESS_MESSAGE)
    }

    fun outputUserTurn(player: Player) {
        print(OUTPUT_USER_TURN_MESSAGE.format(getStoneName(player)))
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
        println(OUTPUT_GAME_START_MESSAGE)
    }

    fun outputFailureMessage(throwable: Throwable) {
        println(throwable.message)
    }

    private fun getStoneName(player: Player): String {
        return when (player) {
            is BlackPlayer -> BLACK_STONE_NAME
            is WhitePlayer -> WHITE_STONE_NAME
        }
    }
}
