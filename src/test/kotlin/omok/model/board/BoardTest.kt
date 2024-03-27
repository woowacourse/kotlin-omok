package omok.model.board

import omok.model.position.Position
import omok.model.rule.X_A
import omok.model.rule.Y_5
import omok.model.stone.BlackStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    @BeforeEach
    fun setUp() {
        Board.reset()
        Board.updateLastPosition(null)
    }

    @Test
    fun `이전 순서에 돌을 두지 않았다면 null을 반환한다`() {
        val actual = Board.lastPosition
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이전 순서에 돌을 두었다면 마지막에 돌을 둔 위치를 반환한다`() {
        val stone = BlackStone()
        stone.putStone(Position(X_A, Y_5))
        val actual = Board.lastPosition
        val expected = Position(X_A, Y_5)

        assertThat(actual).isEqualTo(expected)
    }
}
