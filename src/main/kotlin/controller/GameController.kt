package controller

import controller.ColorMapper.toDTO
import controller.StoneMapper.toDTO
import controller.VectorMapper.toDTO
import domain.Board
import domain.Color
import domain.Coordinate
import domain.Players
import domain.RenjuRule
import domain.Stones
import dto.VectorDTO
import error.ErrorHandler
import error.OmokResult
import view.GameView

class GameController(private val gameView: GameView, private val errorHandler: ErrorHandler) {
    fun process() {
        val players = Players()
        val stones = Stones()
        val omokRule = RenjuRule(stones)
        val board = Board(players, stones)
        gameView.startGame()
        val winner = board.repeatTurn({
            readStone(it, stones)
        }, omokRule, errorHandler)
        renderBoard(stones)
        gameView.renderWinner(winner.color.toDTO())
    }

    private fun readStone(color: Color, stones: Stones): Coordinate {
        renderBoard(stones)

        val vectorDTO = when (val pointResult = gameView.readStone(color.toDTO(), getStoneCoordinateOrNull(stones))) {
            is OmokResult.Success<*> -> {
                pointResult.value as VectorDTO
            }
            else -> {
                errorHandler.log(pointResult)
                return readStone(color, stones)
            }
        }

        val coordinate = when (val coordinateResult = Coordinate.from(vectorDTO.x, vectorDTO.y)) {
            is OmokResult.Success<*> -> {
                coordinateResult.value as Coordinate
            }
            else -> {
                errorHandler.log(coordinateResult)
                return readStone(color, stones)
            }
        }

        return coordinate
    }

    private fun getStoneCoordinateOrNull(stones: Stones): VectorDTO? {
        return if (stones.value.isEmpty()) null
        else stones.value.last().coordinate.vector.toDTO()
    }

    private fun renderBoard(stones: Stones) {
        gameView.renderBoard(
            stones.value.map {
                it.toDTO()
            },
            Board.BOARD_SIZE.toDTO()
        )
    }
}
