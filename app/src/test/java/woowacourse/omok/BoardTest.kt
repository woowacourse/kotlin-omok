package omock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.LoadMap
import woowacourse.omok.model.search.Direction
import woowacourse.omok.model.stone.Stone

class BoardTest {
    @Test
    fun `플레이어가 돌을 놓을 때, direction에 따른 정상적인 돌 개수 반환한다`() {
        val player = WhitePlayer()
        val board = Board.from()
        val loadMap = LoadMap(board.stoneStates)
        val stone = Stone.from(Row("1"), Column("A"))

        board.makeStones(
            player = player,
            coordinates =
                arrayOf("2B", "1B", "2A", "3A", "4A", "5A"),
        )
        board.setStoneState(player, stone)

        loadMap.loadMap(stone).apply {
            assertThat(this[Direction.TOP]?.count).isEqualTo(4)
            assertThat(this[Direction.TOP_RIGHT]?.count).isEqualTo(1)
            assertThat(this[Direction.RIGHT]?.count).isEqualTo(1)
            assertThat(this[Direction.RIGHT_BOTTOM]?.count).isEqualTo(0)
            assertThat(this[Direction.BOTTOM_LEFT]?.count).isEqualTo(0)
            assertThat(this[Direction.LEFT]?.count).isEqualTo(0)
            assertThat(this[Direction.LEFT_TOP]?.count).isEqualTo(0)
        }
    }

    @Test
    fun `플레이어가 이미 돌이 놓여 있는 위치에 돌을 놓으려 할 때, 예외를 던진다`() {
        val player = WhitePlayer()
        val board = Board.from()
        val stone = Stone.from(Row("1"), Column("A"))

        board.makeStones(
            player = player,
            coordinates =
                arrayOf("1A"),
        )

        assertThrows<IllegalArgumentException> { board.setStoneState(player, stone) }
    }

    @Test
    fun `플레이어가 인덱스를 벗어난 위치에 돌을 놓을 때, 예외를 던진다`() {
        val player = WhitePlayer()
        val board = Board.from()

        board.makeStones(
            player = player,
            coordinates =
                arrayOf("1A"),
        )

        assertThrows<IllegalArgumentException> {
            val stone = Stone.from(Row("16"), Column("A"))
            board.setStoneState(player, stone)
        }
    }
}
