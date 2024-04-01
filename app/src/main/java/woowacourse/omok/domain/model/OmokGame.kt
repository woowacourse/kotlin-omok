package woowacourse.omok.domain.model

class OmokGame(private val boardSize: Int) {
    var board: Board = Board(boardSize)
        private set
    var ruleAdapter: RuleAdapter = RuleAdapter(board)
        private set
    var turn: Turn = BlackTurn()
        private set
    var gameState: Boolean = true
        private set
    var beforePoint: Point? = null
        private set

    fun gameReSet() {
        board = Board(boardSize)
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
}
