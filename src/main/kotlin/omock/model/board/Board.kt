package omock.model.board

import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.Node
import omock.model.player.Player
import omock.model.position.Column.Companion.MAX_COLUMN_INDEX
import omock.model.position.Column.Companion.MIN_COLUMN_INDEX
import omock.model.position.Row.Companion.MAX_ROW
import omock.model.position.Row.Companion.MAX_ROW_INDEX
import omock.model.position.Row.Companion.MIN_ROW_INDEX
import omock.model.stone.Stone
import omock.model.stonestate.unplaced.Clear
import omock.model.stonestate.StoneState

class Board(val stoneStates: List<ColumnStates>) {
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

    fun loadMap(stone: Stone): Map<Direction, DirectionResult> {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction, DirectionResult>()
        val playerState = stoneStates[row].getStoneState(column)

        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirection(node, direction, playerState)
        }
        return visited
    }

    private fun getResultWithDirection(
        node: Node,
        direction: Direction,
        playerState: StoneState,
    ): DirectionResult {
        val queue = ArrayDeque<Node>().apply { add(node) }
        var isClear = false
        var isLastClear = true
        var count = 0
        var flag = false
        do {
            if (queue.isEmpty()) break
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.y to current.y + direction.x

            if (!isBoardIndex(nx, ny)) continue
            val nextState = stoneStates[ny].getStoneState(nx)::class

            when (nextState) {
                playerState::class -> {
                    queue.addFirst(Node(nx, ny))
                    count++
                }

                Clear::class -> {
                    if (flag) {
                        isLastClear = true
                    } else {
                        queue.addFirst(Node(nx, ny))
                        isClear = true
                    }
                }

                else -> if (flag) isLastClear = false
            }
            flag = true
        } while (queue.isNotEmpty())

        return DirectionResult(count, isClear, isLastClear)
    }

    private fun isBoardIndex(
        nx: Int,
        ny: Int,
    ): Boolean {
        return nx in MIN_COLUMN_INDEX..MAX_COLUMN_INDEX && ny in MIN_ROW_INDEX..MAX_ROW_INDEX
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
