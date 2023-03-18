package controller

import controller.error.ErrorHandler
import domain.Board
import domain.Color
import domain.Coordinate
import domain.Players
import domain.RenjuRule
import domain.Stones
import dto.VectorDTO
import view.GameView
import java.lang.Exception

class GameController(private val gameView: GameView, private val errorHandler: ErrorHandler) {
    fun process() {
        val players = Players()
        val stones = Stones()
        val omokRule = RenjuRule(stones)
        val board = Board(players, stones, omokRule)
        gameView.startGame()
        val winner = board.repeatTurn {
            readStone(it, stones)
        }
        renderBoard(stones)
        gameView.renderWinner(ColorMapper.domainToDTO(winner.color))
    }

    private fun readStone(color: Color, stones: Stones): Coordinate {
        renderBoard(stones)

        val pointDto = gameView.readStone(ColorMapper.domainToDTO(color), getStoneCoordinateOrNull(stones)).getOrElse {
            errorHandler.log(it as Exception)
            return readStone(color, stones)
        }

        val point = VectorMapper.dtoToDomain(pointDto)
        val coordinate = Coordinate.from(point.x, point.y).getOrElse {
            errorHandler.log(it as Exception)
            return readStone(color, stones)
        }

        return coordinate
    }

    private fun getStoneCoordinateOrNull(stones: Stones): VectorDTO? {
        return if (stones.value.isEmpty()) null
        else VectorMapper.domainToDTO(stones.value.last().coordinate.vector)
    }

    private fun renderBoard(stones: Stones) {
        gameView.renderBoard(
            stones.value.map {
                StoneMapper.domainToDTO(it)
            },
            VectorMapper.domainToDTO(Board.BOARD_SIZE)
        )
    }
}
