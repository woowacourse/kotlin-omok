package omok.model.board

import omok.model.position.Position
import omok.model.resetBoard
import omok.model.resetPosition
import omok.model.stone.BlackStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    @BeforeEach
    fun setUp() {
        resetBoard()
        resetPosition(Board, "lastPosition")
    }

    @Test
    fun `마지막 돌이 없다면 null을 반환한다`() {
        val actual = Board.getLastStonePosition()
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌이 있다면 돌의 위치를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.getLastStonePosition()
        val expected = Position(1, 5)
        assertThat(actual).isEqualTo(expected)
    }
}
