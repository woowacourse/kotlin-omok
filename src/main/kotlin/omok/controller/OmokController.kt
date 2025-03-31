package omok.controller

import omok.model.board.BoardImpl
import omok.model.omokGame.OmokGameImpl
import omok.model.player.BlackPlayerState
import omok.model.player.Finish
import omok.model.player.PlayerState
import omok.model.rule.OmokRuleAdapter
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
