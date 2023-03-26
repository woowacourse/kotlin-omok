package woowacourse.omok

import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import woowacourse.omok.controller.ConsoleOmokController
import woowacourse.omok.view.OmokInputView
import woowacourse.omok.view.OmokOutputView

suspend fun main() {
    ConsoleOmokController(
        OmokInputView(),
        OmokOutputView(),
    ).startGame(
        BlackRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE),
        WhiteRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE)
    )
}
