package omock

import omock.model.Clear
import omock.model.Column
import omock.model.ColumnStates
import omock.model.Direction
import omock.model.Player
import omock.model.Row
import omock.model.Stone
import omock.model.StoneState
import omock.model.WhitePlayer
import org.junit.jupiter.api.Test

class Board(val stoneStates: List<ColumnStates>) {

    fun setStoneState(player: Player, stone: Stone) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        stoneStates[row].change(column, player)
    }

    fun calculate(stone: Stone): Boolean {

        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        val node = Node(x = column, y = row)
        val visited = mutableMapOf<Direction, Int>()
        val playerState = stoneStates[row].getStoneState(column)
        Direction.entries.forEach { direction ->
            visited[direction] = bfs(node, direction, playerState)
        }
        println(visited)
        visited.entries.forEach { (key, value) ->
            if (value > 0) {
                val reverseValue = visited[Direction.reverse(key)] ?: 0
                val count = value + reverseValue + 1
                if (count >= 5) return true
            }
        }
        return false
    }

    data class Node(val x: Int, val y: Int)

    fun bfs(node: Node, direction: Direction, playerState: StoneState): Int {
        val queue = ArrayDeque<Node>()
        queue.add(node)
        var answer = 0
        while (queue.isNotEmpty()) {
            val target = queue.removeFirst()
            val nx = target.x + direction.x
            val ny = target.y + direction.y

            if (nx in 0..14 && ny in 0..14) {
                println(stoneStates[nx].getStoneState(ny).toString() + "," + playerState)
                println("$nx, $ny")
                if (stoneStates[nx].getStoneState(ny)::class == playerState::class) {

                    answer++
                    queue.addFirst(Node(nx, ny))
                }
            }
        }

        return answer
    }


    companion object {
        fun from(): Board {
            return Board(stoneStates = Stone.stones.chunked(15).map { stones ->
                ColumnStates(
                    stones.map {
                        Clear(it)
                    }.toMutableList()
                )
            })
        }
    }
}

class BoardTest {
    @Test
    fun `Board는 돌의 상태를 갖는 2차원 배열이 있다`() {
        val player = WhitePlayer
        val board = Board.from()
        val stone1 = Stone.from(Row("2"), Column("B"))
        val stone2 = Stone.from(Row("1"), Column("B"))
        val stone3 = Stone.from(Row("2"), Column("A"))
        val stone5 = Stone.from(Row("3"), Column("A"))
        val stone6 = Stone.from(Row("4"), Column("A"))
        val stone4 = Stone.from(Row("5"), Column("A"))


        val stone = Stone.from(Row("1"), Column("A"))
        board.setStoneState(player, stone)

        board.setStoneState(player, stone1)
        board.setStoneState(player, stone2)
        board.setStoneState(player, stone3)
        board.setStoneState(player, stone4)
        board.setStoneState(player, stone5)
        board.setStoneState(player, stone6)
        println(board.calculate(stone))

    }
}
