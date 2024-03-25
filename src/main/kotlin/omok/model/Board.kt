package omok.model

import omok.controller.retryUntilNotException
import omok.model.rule.winning.WinningCondition

class Board(
    val size: Int,
    private val _board: MutableMap<Position, Stone> = initBoard(size),
) {
    val board: Map<Position, Stone>
        get() = _board.toMap()
    val startIndex = 0
    val endIndex = size - 1

    fun place(
        position: Position,
        player: Player,
    ) {
        requireNotNull(findOrNull(position)) { INVALID_POSITION_MESSAGE }
        require(findOrNull(position) == Stone.NONE) { DUPLICATION_POSITION_MESSAGE }
        require(player.canPlace(this, position)) { FORBIDDEN_POSITION_MESSAGE }

        _board[position] = player.stone
    }

    fun findOrNull(position: Position): Stone? = _board[position]

    fun gameWinner(
        omokTurnAction: OmokTurnAction,
        omokPlayers: OmokPlayers,
        winningCondition: WinningCondition,
    ): FinishType {
        var recentPlayer = omokPlayers.firstOrderPlayer()
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(omokTurnAction, recentPlayer)
            omokTurnAction.onStonePlace(this)
            if (winningCondition.isWin(this, recentPosition)) break
            if (isFull()) return FinishType.DRAW
            recentPlayer = omokPlayers.next(recentPlayer)
        }
        return omokPlayers.winningFinishType(recentPlayer)
    }

    private fun Position?.next(
        omokTurnAction: OmokTurnAction,
        recentPlayer: Player,
    ): Position {
        return retryUntilNotException {
            val nextPosition = omokTurnAction.nextStonePosition(recentPlayer.stone, this)
            place(nextPosition, recentPlayer)
            nextPosition
        }
    }

    private fun isFull(): Boolean {
        return _board.all { it.value != Stone.NONE }
    }

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바르지 않은 위치입니다."
        private const val DUPLICATION_POSITION_MESSAGE = "이미 바둑돌이 있는 위치입니다."
        private const val FORBIDDEN_POSITION_MESSAGE = "바둑돌을 놓을 수 없는 위치입니다."

        private fun initBoard(size: Int) =
            (0 until size).flatMap { row ->
                (0 until size).map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
