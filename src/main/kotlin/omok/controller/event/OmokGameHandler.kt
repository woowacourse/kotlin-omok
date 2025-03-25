package omok.controller.event

import omok.domain.board.BoardStatus
import omok.domain.point.Point
import omok.domain.service.OmokGame
import omok.domain.stone.StoneColor
import omok.exception.recover
import omok.view.GameView

class OmokGameHandler(
    private val gameView: GameView,
    private val omokGame: OmokGame,
) : GameEventListener {
    override fun onCompleteInputPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): Point {
        gameView.printBoard(board, stone)
        val point = gameView.readStoneWithLatestStone(stone, omokGame.latestStone)
        return Point.of(point, stone)
            .recover {
                gameView.printErrorMessage(it)
                onCompleteInputPoint(stone, board)
            }
    }

    override fun onFailToAddStone(message: String?) {
        gameView.printErrorMessage(message)
    }

    override fun onFinishedGame(color: StoneColor) {
        gameView.printWinner(color)
    }
}
