package omok.controller

import woowacourse.omok.model.board.BoardImpl
import woowacourse.omok.model.omokGame.OmokGameImpl
import woowacourse.omok.model.player.BlackPlayerState
import woowacourse.omok.model.player.Finish
import woowacourse.omok.model.player.PlayerState
import woowacourse.omok.model.rule.OmokRuleAdapter
import omok.view.OmokView

class OmokController(
    private val omokView: OmokView,
) {
    fun run() {
        omokView.printStartMessage()
        val board = BoardImpl.createEmpty()
        val rule = OmokRuleAdapter()
        val game = OmokGameImpl(board, rule)
        var playerState: PlayerState = BlackPlayerState(game)

        while (true) {
            val position = omokView.inputPosition(playerState)
            playerState = playerState.state(position)
            omokView.printBoard(board.board)
            if (playerState is Finish) break
        }
        val winner = (playerState as Finish).winner()
        omokView.result(winner)
    }
}
