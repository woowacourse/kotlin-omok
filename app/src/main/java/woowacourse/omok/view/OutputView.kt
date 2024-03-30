package woowacourse.omok.view

import woowacourse.omok.view.LocalBoard.boardForm
import woowacourse.omok.view.LocalBoard.boardTable
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.stone.Stone

object OutputView {
    private const val REQUIRE_LOCATION_MESSAGE = "위치를 입력하세요:"
    const val OUTPUT_SUCCESS_MESSAGE = "오목~!~!~!~!~!~"
    private const val OUTPUT_USER_TURN_MESSAGE = "%s의 차례입니다."
    private const val OUTPUT_GAME_START_MESSAGE = "오목 게임을 시작합니다."

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
            is BlackPlayer -> Stone.BLACK_STONE_NAME
            is WhitePlayer -> Stone.WHITE_STONE_NAME
        }
    }
}
