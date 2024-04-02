package omok.model.board

import omok.model.position.Position
import omok.model.resetBoard
import omok.model.resetPosition
import omok.model.stone.BlackStone
import omok.model.stone.StoneType
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
    fun `마지막 돌이 없다면 false를 반환한다`() {
        val actual = Board.isLastPositionExist()
        val expected = false
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌이 있다면 true를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.isLastPositionExist()
        val expected = true
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 마지막 위치를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.getLastStonePosition()
        val expected = Position(1, 5)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌의 위치를 변경한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        Board.changeLastStonePosition(Position(2, 5))
        val actual = Board.getLastStonePosition()
        val expected = Position(2, 5)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌이 보드의 범위에 있는 경우 true를 반환한다`() {
        val actual = Board.isPositionInRange(1, 5)
        val expected = true
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌이 보드의 범위를 벗어난 경우 false를 반환한다`() {
        val actual = Board.isPositionInRange(1, 16)
        val expected = false
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정 위치의 돌의 색이 같은 경우 true를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.isSameStone(1, 5, StoneType.BLACK_STONE)
        val expected = true
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정 위치의 돌의 색이 다른 경우 false를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.isSameStone(1, 5, StoneType.WHITE_STONE)
        val expected = false
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `특정 위치의 돌의 색을 가져온다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        val actual = Board.getStoneType(5, 1)
        val expected = StoneType.BLACK_STONE
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `판을 초기화 시킨다`() {
        val stone = BlackStone
        stone.putStone(Position(1, 5))
        Board.resetBoard()
        val actual = Board.getStoneType(5, 1)
        val expected = StoneType.NONE
        assertThat(actual).isEqualTo(expected)
    }
}
