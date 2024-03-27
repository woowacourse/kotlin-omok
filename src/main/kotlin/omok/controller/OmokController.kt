package omok.controller

import omok.model.board.Board
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.game.OmokGame
import omok.model.game.OmokPlayers
import omok.model.game.OmokTurnAction
import omok.model.player.Player
import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.OverlineForbiddenPlace
import omok.view.BoardView
import omok.view.ResultView
import omok.view.StartView
import omok.view.StonePositionView

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
