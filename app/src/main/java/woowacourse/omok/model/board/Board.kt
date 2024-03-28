package woowacourse.omok.model.board

import woowacourse.omok.model.GameTurn
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.position.Row.Companion.MAX_ROW
import woowacourse.omok.model.search.VisitedDirectionResult
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stonestate.Clear

class Board(val stoneStates: List<ColumnStates>) {
    private var gameTurn = GameTurn.BLACK_TURN

    fun getTurn(): GameTurn {
        return gameTurn
    }

    fun applyPlayerJudgement(
        player: Player,
        visitedDirectionResult: VisitedDirectionResult,
    ) {
        when (player.judgementResult(visitedDirectionResult)) {
            true -> finishTurn()
            false -> turnOff()
        }
    }

    private fun finishTurn() {
        gameTurn = GameTurn.FINISHED
    }

    private fun turnOff() {
        gameTurn = gameTurn.turnOff()
    }

    fun setStoneState(
        player: Player,
        stone: Stone,
    ) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        stoneStates[row].change(column, player)
    }

    fun rollbackState(stone: Stone) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        stoneStates[row].rollback(column)
    }

    companion object {
        fun from(): Board {
            return Board(
                stoneStates =
                    Stone.stones.chunked(MAX_ROW).map { stones ->
                        ColumnStates(
                            stones.map {
                                Clear(it)
                            }.toMutableList(),
                        )
                    },
            )
        }
    }
}
