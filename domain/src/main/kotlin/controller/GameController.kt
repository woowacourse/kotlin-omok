package controller

import domain.Board
import domain.Color
import domain.Coordinate
import domain.Stones
import dto.PointDTO
import view.ConsoleGameView
import view.GameView

class GameController(
    private val gameView: GameView = ConsoleGameView()
) {
    fun process() {
        val stones = Stones()
        val board = Board(stones)
        gameView.startGame()
        board.repeatTurn {
            readStone(it, stones)
        }
        renderBoard(stones)
        val winnerColor = board.getLastColor()
        gameView.renderWinner(ColorMapper.domainToDTO(winnerColor))
    }

    private fun readStone(color: Color, stones: Stones): Coordinate {
        renderBoard(stones)
        val pointDto = gameView.readStone(
            ColorMapper.domainToDTO(color), getStoneCoordinateOrNull(stones)
        ) ?: return readStone(color, stones)

        val point = PointMapper.dtoToDomain(pointDto)
        return Coordinate.from(point.x, point.y) ?: return readStone(color, stones)
    }

    private fun renderBoard(stones: Stones) {
        gameView.renderBoard(
            stones.value.map {
                StoneMapper.domainToDTO(it)
            },
            PointMapper.domainToDTO(Board.BOARD_END_POINT)
        )
    }

    private fun getStoneCoordinateOrNull(stones: Stones): PointDTO? {
        return if (stones.value.isEmpty()) null
        else PointMapper.domainToDTO(stones.value.last().coordinate.point)
    }
}
