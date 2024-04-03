package woowacourse.omok.domain.model

import woowacourse.omok.db.OmokEntity

class OmokGame {
    var board: Board = Board()
        private set
    var turn: Turn = BlackTurn()
        private set

    private var ruleAdapter: RuleAdapter = RuleAdapter(board)

    fun initBoard(omokEntities: List<OmokEntity>) {
        val lastEntity = omokEntities.last()
        val point = Point(lastEntity.pointX, lastEntity.pointY)
        board.initializeTable(omokEntities.dropLast(1))
        putStone(point)
    }

    fun gameReSet() {
        board = Board()
        turn = BlackTurn()
        ruleAdapter = RuleAdapter(board)
    }

    fun putStone(point: Point): Boolean {
        if (ruleAdapter.checkForbidden(point, turn.stoneType)) return false
        val stoneType = turn.stoneType
        turn = turn.nextTurn(ruleAdapter.checkWin(point, turn.stoneType))
        board.putStoneOnTable(point, stoneType)
        return true
    }

    fun onGame(): Boolean = turn !is FinishedTurn

    companion object {
        const val BOARD_SIZE = 15
    }
}
