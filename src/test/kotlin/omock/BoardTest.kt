package omock

import omock.model.Board
import omock.model.Column
import omock.model.Direction
import omock.model.Row
import omock.model.Stone
import omock.model.WhitePlayer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `플레이어가 돌을 놓을 때, direction에 따른 정상적인 돌 개수 반환한다`() {
        val player = WhitePlayer()
        val board = Board.from()
        board.makeStones(
            player = player,
            coordinates =
            arrayOf("2B","1B","2A","3A","4A","5A"),
        )

        val stone = Stone.from(Row("1"), Column("A"))
        board.setStoneState(player, stone)

        val visited = board.loadMap(stone)
        assertThat(visited[Direction.TOP]?.count).isEqualTo(4)
        assertThat(visited[Direction.TOP_RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT_BOTTOM]?.count).isEqualTo(0)
        assertThat(visited[Direction.BOTTOM_LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT_TOP]?.count).isEqualTo(0)
    }
}
