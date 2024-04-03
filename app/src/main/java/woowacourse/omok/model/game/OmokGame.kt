package woowacourse.omok.model.game

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.player.Player

class OmokGame(
    private val board: Board,
    private val finishAction: FinishAction,
    private val turnHistory: TurnHistory,
) {
    private var recentFinishType = FinishType.NOT_FINISH

    fun turn(position: Position): PlaceType {
        return placeType(position, turnHistory.recentPlayer).also {
            checkFinish(position)
            updateTurnHistory(it, position)
        }
    }

    private fun checkFinish(position: Position) {
        recentFinishType = finishType(board, position, turnHistory.recentPlayer)
        if (!recentFinishType.isFinish()) return
        finishAction.onFinish(recentFinishType)
    }

    private fun placeType(
        position: Position,
        turnPlayer: Player,
    ): PlaceType {
        return board.place(position, turnPlayer)
    }

    private fun updateTurnHistory(
        placeType: PlaceType,
        position: Position,
    ) {
        if (!placeType.canPlace()) return
        turnHistory.update(position)
    }

    fun nowOrderStone() = turnHistory.recentPlayer.stone

    fun recentPosition() = turnHistory.recentPosition

    fun restart() {
        recentFinishType = FinishType.NOT_FINISH
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

    fun isFinish() = recentFinishType.isFinish()
}
