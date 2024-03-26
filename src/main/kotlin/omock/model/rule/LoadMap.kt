package omock.model.rule

import omock.model.board.ColumnStates
import omock.model.position.Column
import omock.model.position.Row
import omock.model.search.Direction
import omock.model.search.DirectionFirstClearResult
import omock.model.search.DirectionResult
import omock.model.search.Node
import omock.model.stone.Stone
import omock.model.stonestate.Black
import omock.model.stonestate.Clear
import omock.model.stonestate.StoneState
import omock.model.stonestate.White

class LoadMap(private val stoneStates: List<ColumnStates>) : LoadMapInterface {
    override fun loadMap(stone: Stone): Map<Direction, DirectionResult> {
        val node = getCurrentNode(stone)
        val visited = mutableMapOf<Direction, DirectionResult>()
        val playerState = stoneStates[node.y].getStoneState(node.x)

        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirection(node, direction, playerState)
        }
        return visited
    }

    override fun firstClearLoadMap(stone: Stone): Map<Direction, DirectionFirstClearResult> {
        val node = getCurrentNode(stone)
        val visited = mutableMapOf<Direction,DirectionFirstClearResult>()
        Direction.entries.forEach { direction ->
            visited[direction] = getResultWithDirectionFirstClear(node,direction)
        }
        return visited
    }

    override fun getCurrentNode(stone: Stone): Node {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        return Node(x = column, y = row)
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
        return nx in Column.MIN_COLUMN_INDEX..Column.MAX_COLUMN_INDEX && ny in Row.MIN_ROW_INDEX..Row.MAX_ROW_INDEX
    }
}
