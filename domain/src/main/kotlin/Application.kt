import controller.OmokController
import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import view.OmokInputView
import view.OmokOutputView

suspend fun main() {
    OmokController(
        OmokInputView(),
        OmokOutputView(),
    ).startGame(
        BlackRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE),
        WhiteRenjuRule(OMOK_BOARD_SIZE, OMOK_BOARD_SIZE)
    )
}
