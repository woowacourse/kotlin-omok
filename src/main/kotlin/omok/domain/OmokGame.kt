package omok.domain

import omok.domain.grid.OmokGrid
import omok.domain.grid.OmokPoint

class OmokGame(val grid: OmokGrid) {
    private val referee = Referee()

    fun getStartingPlayer(): StoneColor {
        return StoneColor.BLACK
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
    }

    fun playMove(
        stoneColor: StoneColor,
        point: OmokPoint,
    ) {
        grid.putStone(point, stoneColor)
    }

    fun changeTurn(nowTurn: StoneColor): StoneColor {
        return if (nowTurn == StoneColor.BLACK) {
            StoneColor.WHITE
        } else {
            StoneColor.BLACK
        }
    }

    fun validatePoint(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ) {
        referee.checkViolation(nowTurn, grid.getStones(StoneColor.BLACK), grid.getStones(StoneColor.WHITE), startPoint)
    }

    fun checkWin(
        nowTurn: StoneColor,
        startPoint: OmokPoint,
    ): Boolean {
        return referee.checkWin(nowTurn, grid.getStones(nowTurn), startPoint)
    }
}
