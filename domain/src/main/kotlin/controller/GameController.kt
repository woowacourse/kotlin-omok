package controller

import controller.ColorMapper.toDTO
import controller.StoneMapper.toDTO
import controller.VectorMapper.toDTO
import domain.Board
import domain.Coordinate
import domain.Stone
import domain.Stones
import domain.Vector
import domain.omokrule.RenjuRule
import dto.VectorDTO
import error.CoordinateError
import error.ErrorHandler
import error.OmokResult
import view.GameView
import view.PlaceStoneObservable

class GameController(private val gameView: GameView, private val errorHandler: ErrorHandler) :
    PlaceStoneObservable {
    private val board = Board()
    private val omokRule = RenjuRule(board.stones)

    fun process() {
        gameView.placeStoneObserver.subscribe(this)
        gameView.renderStart(board.currentPlayer.color.toDTO())
        gameView.setUpInput()
    }

    override fun placeStone(coordinate: VectorDTO): Boolean {
        val validatedCoordinate = validateStone(coordinate) ?: return false
        when (val result = board.repeatTurn(validatedCoordinate, omokRule)) {
            is OmokResult.Success<*> -> {
                if (placeStoneSuccess(result.value as Stone)) return true
            }
            else -> errorHandler.log(result)
        }
        return false
    }

    private fun placeStoneSuccess(
        stone: Stone,
    ): Boolean {
        if (board.isWinPlace(stone, omokRule)) {
            renderBoard(board.stones)
            gameView.renderWinner(stone.color.toDTO())
            return true
        }

        renderBoard(board.stones)
        gameView.renderTurn(
            board.currentPlayer.color.toDTO(), stone.coordinate.vector.toDTO()
        )
        return false
    }

    private fun validateStone(vectorDTO: VectorDTO): Coordinate? {
        val coordinate = Coordinate.from(vectorDTO.x, vectorDTO.y)
        if (coordinate == null) {
            errorHandler.log(CoordinateError.OutOfBoard)
        }
        return coordinate
    }

    private fun renderBoard(stones: Stones) {
        gameView.renderBoard(
            stones.value.associate {
                vectorToScalar(it.coordinate.vector) to it.toDTO()
            }, Board.BOARD_SIZE.toDTO()
        )
    }

    private fun vectorToScalar(vector: Vector): Int {
        val stoneY = (Board.BOARD_SIZE.y - vector.y - 1) * Board.BOARD_SIZE.y
        return (stoneY + vector.x)
    }
}
