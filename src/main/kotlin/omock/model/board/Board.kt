package omock.model.board

import omock.model.GameTurn
import omock.model.player.Player
import omock.model.position.Row.Companion.MAX_ROW
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone
import omock.model.stonestate.Clear

class Board(val stoneStates: List<ColumnStates>) {
    private var gameTurn = GameTurn.BLACK_TURN

    fun getTurn(): GameTurn{
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

    private fun finishTurn(){
        gameTurn = GameTurn.FINISHED
    }

    private fun turnOff(){
        gameTurn.turnOff()
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
