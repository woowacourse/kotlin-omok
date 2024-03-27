package omock

import omock.model.board.Board
import omock.model.player.WhitePlayer
import omock.model.position.Column
import omock.model.position.Row
import omock.model.rule.LoadMap
import omock.model.search.Direction
import omock.model.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `플레이어가 돌을 놓을 때, direction에 따른 정상적인 돌 개수 반환한다`() {
        val player = WhitePlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        board.makeStones(
            player = player,
            coordinates =
            arrayOf("2B", "1B", "2A", "3A", "4A", "5A"),
        )

        val stone = Stone.from(Row("1"), Column("A"))
        board.setStoneState(player, stone)

        val visited = loadMap.loadMap(stone)
        assertThat(visited[Direction.TOP]?.count).isEqualTo(4)
        assertThat(visited[Direction.TOP_RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT]?.count).isEqualTo(1)
        assertThat(visited[Direction.RIGHT_BOTTOM]?.count).isEqualTo(0)
        assertThat(visited[Direction.BOTTOM_LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT]?.count).isEqualTo(0)
        assertThat(visited[Direction.LEFT_TOP]?.count).isEqualTo(0)
    }

    @Test
    fun `플레이어가 이미 돌이 놓여 있는 위치에 돌을 놓으려 할 때, 예외를 던진다`() {
        val player = WhitePlayer()
        val board = Board.from()
        board.makeStones(
            player = player,
            coordinates =
                arrayOf("1A")
        )

        val stone = Stone.from(Row("1"), Column("A"))
        assertThrows<IllegalArgumentException> { board.setStoneState(player, stone)}
    }


    @Test
    fun `플레이어가 인덱스를 벗어난 위치에 돌을 놓을 때, 예외를 던진다`() {
        val player = WhitePlayer()
        val board = Board.from()
        board.makeStones(
            player = player,
            coordinates =
            arrayOf("1A")
        )
        assertThrows<IllegalArgumentException> {
            val stone = Stone.from(Row("16"), Column("A"))
            board.setStoneState(player, stone)
        }
    }
}
