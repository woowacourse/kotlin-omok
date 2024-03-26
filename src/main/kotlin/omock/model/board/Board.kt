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
import omock.model.search.DirectionFirstClearResult
import omock.model.stone.Stone
import omock.model.stonestate.Black
import omock.model.stonestate.Clear
import omock.model.stonestate.StoneState
import omock.model.stonestate.White

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

    fun firstClearLoadMap(stone: Stone): Map<Direction, DirectionFirstClearResult>{
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction,DirectionFirstClearResult>()
        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirectionFirstClear(node,direction)
        }
        return visited
    }

    private fun getResultWithDirectionFirstClear(
        node: Node,
        direction: Direction,
    ): DirectionFirstClearResult{
        val (nx, ny) = node.x + direction.y to node.y + direction.x
        if (!isBoardIndex(nx, ny)) return DirectionFirstClearResult(false)
        val nextState = stoneStates[ny].getStoneState(nx)
        return DirectionFirstClearResult(nextState is Clear)
    }

    private fun getResultWithDirection(
        node: Node,
        direction: Direction,
        playerState: StoneState,
    ): DirectionResult {
        val queue = ArrayDeque<Node>().apply { add(node) }
        var isLastClear = true
        var count = 0
        var flag = false

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (nx, ny) = current.x + direction.y to current.y + direction.x
            fun addFindState(){
                queue.addFirst(Node(nx, ny))
                count++
            }

            if (!isBoardIndex(nx, ny)) continue
            val nextState = stoneStates[ny].getStoneState(nx)

            when (nextState) {
                is Black -> {
                    if (playerState is Black){
                        addFindState()
                    }else{
                        isLastClear = !flag
                    }
                }

                is White -> {
                    if (playerState is White){
                        addFindState()
                    }else{
                        isLastClear = !flag
                    }
                }

                is Clear -> {
                    if (flag) {
                        isLastClear = true
                    } else {
                        queue.addFirst(Node(nx, ny))
                    }
                }
            }
            flag = true
        }

        return DirectionResult(count, isLastClear)
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
