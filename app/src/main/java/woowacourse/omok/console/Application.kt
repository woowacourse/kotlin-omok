package woowacourse.omok.console

import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import woowacourse.omok.controller.ConsoleOmokController

suspend fun main() {
    ConsoleOmokController(
        OmokInputView(),
        OmokOutputView(),
    ).startGame(
        BlackRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE),
        WhiteRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE)
    )
}
