package woowacourse.omok.controller

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.game.OmokTurnAction
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.rule.ban.DoubleFourForbiddenPlace
import woowacourse.omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import woowacourse.omok.model.rule.ban.OverlineForbiddenPlace
import woowacourse.omok.view.BoardView
import woowacourse.omok.view.ResultView
import woowacourse.omok.view.StartView
import woowacourse.omok.view.StonePositionView

class OmokController(
    private val stonePositionView: StonePositionView,
    private val startView: StartView,
    private val boardView: BoardView,
    private val resultView: ResultView,
    private val boardSize: Int,
) {
    private val omokPlayers: OmokPlayers

    init {
        val blackForbiddenPlaces =
            listOf(
                DoubleFourForbiddenPlace(),
                DoubleOpenThreeForbiddenPlace(),
                OverlineForbiddenPlace(),
            )

        omokPlayers =
            OmokPlayers(
                blackStonePlayer = Player(Stone.BLACK, blackForbiddenPlaces),
                whiteStonePlayer = Player(Stone.WHITE),
            )
    }

    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board, omokPlayers)
        val finishType = omokGame.gameResult(omokTurnAction())
        resultView.print(finishType)
    }

    private fun initializedBoard(): Board {
        return Board(boardSize).apply { startView.print(this, boardView) }
    }

    private fun omokTurnAction(): OmokTurnAction {
        return object : OmokTurnAction {
            override fun nextStonePosition(
                nowOrderStone: Stone,
                recentPosition: Position?,
            ): Position {
                return stonePositionView.read(boardSize, nowOrderStone, recentPosition)
            }

            override fun onStonePlace(board: Board) {
                boardView.print(board)
            }
        }
    }
}
