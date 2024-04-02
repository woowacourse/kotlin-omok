package woowacourse.omok.domain.model

import woowacourse.omok.db.OmokEntity

class OmokGame {
    var board: Board = Board()
        private set
    var ruleAdapter: RuleAdapter = RuleAdapter(board)
        private set
    var turn: Turn = BlackTurn()
        private set
    var gameState: Boolean = true
        private set
    var beforePoint: Point? = null
        private set

    fun initBoard(omokEntities: List<OmokEntity>) {
        val lastEntity = omokEntities.last()
        val point = Point(lastEntity.pointX, lastEntity.pointY)
        board.initializeTable(omokEntities.dropLast(1))
        updateTurn(board.putStone(point, judgeTurn(lastEntity), ruleAdapter))
        updateBeforePoint(point)
    }

    fun gameReSet() {
        board = Board()
        ruleAdapter = RuleAdapter(board)
        turn = BlackTurn()
        gameState = true
        beforePoint = null
    }

    fun updateGameState(_gameState: Boolean) {
        gameState = _gameState
    }

    fun updateTurn(_turn: Turn) {
        turn = _turn
    }

    fun updateBeforePoint(_beforePoint: Point) {
        beforePoint = _beforePoint
    }

    fun judgeGameState() {
        if (beforePoint != null) {
            updateGameState(
                !ruleAdapter.checkWin(
                    beforePoint!!,
                    turn.stoneType,
                ),
            )
        }
    }

    private fun judgeTurn(omokEntity: OmokEntity): Turn {
        return when (omokEntity.stoneType) {
            BlackTurn().stoneType.name -> BlackTurn()
            WhiteTurn().stoneType.name -> WhiteTurn()
            else -> FinishedTurn(turn.stoneType)
        }
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
