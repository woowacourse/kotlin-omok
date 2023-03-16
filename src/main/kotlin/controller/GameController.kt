package controller

import domain.BlackPlayer
import domain.Board
import domain.Color
import domain.Coordinate
import domain.Players
import domain.Stones
import domain.WhitePlayer
import dto.PointDTO
import view.ConsoleGameView

class GameController(
    private val gameView: ConsoleGameView = ConsoleGameView()
) {

    private fun renderBoard(stones: Stones) {
        gameView.renderBoard(
            stones.value.map {
                StoneMapper.domainToDTO(it)
            },
            PointMapper.domainToDTO(Board.BOARD_SIZE)
        )
    }

    private fun getStoneCoordinateOrNull(stones: Stones): PointDTO? {
        return if (stones.value.isEmpty()) null
        else PointMapper.domainToDTO(stones.value.last().coordinate.point)
    }

    private fun readStone(color: Color, stones: Stones): Coordinate {
        renderBoard(stones)

        val pointDto = gameView.readStone(
            ColorMapper.domainToDTO(color), getStoneCoordinateOrNull(stones)
        ) ?: return readStone(color, stones)

        val point = PointMapper.dtoToDomain(pointDto)
        return Coordinate.from(point.x, point.y) ?: return readStone(color, stones)
    }

    fun process() {
        val players = Players(listOf(BlackPlayer(), WhitePlayer()))
        val stones = Stones()
        val board = Board(players, stones)
        gameView.startGame()
        val winner = board.repeatTurn {
            readStone(it, stones)
        }
        renderBoard(stones)
        gameView.renderWinner(ColorMapper.domainToDTO(winner.color))
    }
}
