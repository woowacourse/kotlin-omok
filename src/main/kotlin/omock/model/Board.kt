package omock.model

import omock.model.Column.Companion.MAX_COLUMN_INDEX
import omock.model.Column.Companion.MIN_COLUMN_INDEX
import omock.model.Row.Companion.MAX_ROW
import omock.model.Row.Companion.MAX_ROW_INDEX
import omock.model.Row.Companion.MIN_ROW_INDEX


class Board(val stoneStates: List<ColumnStates>) {

    fun setStoneState(
        player: Player,
        stone: Stone,
    ) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        stoneStates[column].change(row, player)
    }


    fun loadMap(stone: Stone): Map<Direction, Result> {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction, Result>()
        val playerState = stoneStates[column].getStoneState(row)
        Direction.entries.forEach { direction ->
            visited[direction] = bfs(node, direction, playerState)
        }
        return visited
    }


    private fun bfs(
        node: Node,
        direction: Direction,
        playerState: StoneState,
    ): Result {
        val queue = ArrayDeque<Node>().apply { add(node) }
        var isClear = false
        var isLastClear = true
        var count = 0
        var flag = false
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.x to current.y + direction.y

            if (!(nx in MIN_COLUMN_INDEX..MAX_COLUMN_INDEX && ny in MIN_ROW_INDEX..MAX_ROW_INDEX)) continue

            val nextState = stoneStates[nx].getStoneState(ny)::class

            if (!flag) {
                flag = true
                when (nextState) {
                    playerState::class -> {
                        queue.addFirst(Node(nx, ny))
                        count++
                    }

                    Clear::class -> {
                        queue.addFirst(Node(nx, ny))
                        isClear = true
                    }
                }
                continue
            }

            when (nextState) {
                playerState::class -> {
                    count++
                    queue.addFirst(Node(nx, ny))
                }

                Clear::class -> isLastClear = true
                else -> isLastClear = false
            }
        }

        return Result(count, isClear, isLastClear)
    }


    companion object {
        fun from(): Board {
            return Board(stoneStates = Stone.stones.chunked(MAX_ROW).map { stones ->
                ColumnStates(
                    stones.map {
                        Clear(it)
                    }.toMutableList()
                )
            })
        }
    }
}
