import woowacourse.omok.omok.lib.RenjuGameRule
import woowacourse.omok.omok.model.board.CoordsNumber
import woowacourse.omok.omok.model.board.Position
import woowacourse.omok.omok.model.board.Stone
import woowacourse.omok.omok.model.omokGame.Board

class GameRuleAdapter(private val renjuGameRule: RenjuGameRule = RenjuGameRule()) {
    fun setupBoard(board: Board) {
        val intBoard =
            board.gameBoard.map { row ->
                row.map { stone -> stone.value }.toTypedArray()
            }.toTypedArray()
        renjuGameRule.setupBoard(intBoard)
    }

    fun isWinningMove(
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        stone: Stone,
    ): Boolean {
        return renjuGameRule.isWinningMove(rowCoords.number, columnCoords.number, stone.value)
    }

    fun findForbiddenPositions(stone: Stone): List<Position> {
        if (stone == Stone.WHITE) return listOf()
        return renjuGameRule.findForbiddenPositions(stone.value)
            .map { Position(CoordsNumber(it.first), CoordsNumber(it.second)) }
    }

    fun placeForRuleCheck(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ) {
        renjuGameRule.placeForRuleCheck(x.number, y.number, stone.value)
    }
}
