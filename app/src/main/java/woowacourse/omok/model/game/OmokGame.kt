package woowacourse.omok.model.game

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.player.Player

class OmokGame(
    private val board: Board,
    private val finishAction: FinishAction,
    private val turnHistory: TurnHistory,
) {
    fun turn(position: Position): PlaceType {
        return placeType(position, turnHistory.recentPlayer).also {
            checkFinish(position)
            updateTurnHistory(it, position)
        }
    }

    private fun checkFinish(position: Position) {
        val finishType = finishType(board, position, turnHistory.recentPlayer)
        if (!finishType.isFinish()) return
        finishAction.onFinish(finishType)
    }

    private fun placeType(
        position: Position,
        turnPlayer: Player,
    ): PlaceType {
        runCatching { board.place(position, turnPlayer) }
            .onFailure { return PlaceType.CANNOT_PLACE }
        return if (turnPlayer.stone == Stone.WHITE) PlaceType.WHITE_PLACE else PlaceType.BLACK_PLACE
    }

    private fun updateTurnHistory(
        placeType: PlaceType,
        position: Position,
    ) {
        if (placeType == PlaceType.CANNOT_PLACE) return
        turnHistory.update(position)
    }

    fun nowOrderStone() = turnHistory.recentPlayer.stone

    fun recentPosition() = turnHistory.recentPosition

    fun restart() {
        turnHistory.clear()
        board.clear()
    }

    private fun finishType(
        board: Board,
        recentPosition: Position,
        player: Player,
    ): FinishType {
        return finishAction.conditions()
            .map { it.finishType(board, recentPosition, player) }
            .minBy { it.ordinal }
    }
}
