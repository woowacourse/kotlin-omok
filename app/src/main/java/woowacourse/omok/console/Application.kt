package woowacourse.omok

import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import woowacourse.omok.console.OmokInputView
import woowacourse.omok.console.OmokOutputView
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
