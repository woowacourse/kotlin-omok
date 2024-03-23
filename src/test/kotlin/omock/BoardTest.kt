package omock

import omock.model.Board
import omock.model.Column
import omock.model.Direction
import omock.model.Row
import omock.model.state.Stone
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `플레이어가 돌을 놓을 때, direction에 따른 정상적인 돌 개수 반환한다`() {
        val player = WhiteTurn()
        val board = Board.from()
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("2"), Column("B")),
                    Stone.from(Row("1"), Column("B")),
                    Stone.from(Row("2"), Column("A")),
                    Stone.from(Row("3"), Column("A")),
                    Stone.from(Row("4"), Column("A")),
                    Stone.from(Row("5"), Column("A")),
                ),
        )

        val stone = Stone.from(Row("1"), Column("A"))
        board.setStoneState(player, stone)

        val visited = player.oMockRule.loadMap(board.stoneStates, stone)

        assertThat(visited[Direction.TOP]?.count).isEqualTo(4)
        assertThat(visited[Direction.TOP_RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT_BOTTOM]?.count).isEqualTo(0)
        assertThat(visited[Direction.BOTTOM_LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT_TOP]?.count).isEqualTo(0)
    }
}

fun Board.makeStones(
    player: Turn,
    vararg stones: Stone,
) {
    stones.forEach { stone ->
        this.setStoneState(player, stone)
    }
}
