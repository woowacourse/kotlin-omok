package omok.board

import omok.stone.Position
import omok.stone.Stone
import omok.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FakeBoard(stones: Set<Stone>) : Board(stones)

class BoardTest {
    @Test
    fun `보드에는 중복된 위치의 돌이 존재할 수 없다`() {
        val stone = Stone(StoneColor.WHITE, Position(1, 1))
        val board = FakeBoard(setOf(stone, stone))

        assertThat(board.points.size).isEqualTo(1)
    }
}
