package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BlackStoneTest {
    @Test
    fun `마지막 돌이 없다면 null을 반환한다`() {
        val stone = BlackStone()
        val actual = stone.getLastPosition()
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 돌이 있다면 돌의 위치를 반환한다`() {
        val stone = BlackStone()
        stone.putStone(Position(1, 5))
        val actual = stone.getLastPosition()
        val expected = Position(1, 5)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓을 경우 예외가 발생한다`() {
        val stone = BlackStone()
        val position = Position(0, 0)
        stone.putStone(position)
        assertThatThrownBy { stone.putStone(position) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미 놓여진 자리입니다.")
    }

    @Test
    fun `유효한 자리에 돌을 놓을 경우 예외가 발생하지 않는다`() {
        val stone = BlackStone()
        val position = Position(1, 2)
        assertDoesNotThrow { stone.putStone(position) }
    }

    @Test
    fun `오목 여부를 판단한다 - 오목일 때`() {
        // given
        val blackStone = BlackStone()

        blackStone.putStone(Position(1, 1))
        blackStone.putStone(Position(2, 2))
        blackStone.putStone(Position(3, 3))
        blackStone.putStone(Position(4, 4))
        blackStone.putStone(Position(5, 5))

        // when
        val actual = blackStone.findOmok()
        val expected = true

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목 여부를 판단한다 - 오목이 아닐 때`() {
        // given
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackStone.putStone(Position(10, 10))
        blackStone.putStone(Position(11, 11))
        whiteStone.putStone(Position(12, 12))
        blackStone.putStone(Position(13, 13))
        blackStone.putStone(Position(14, 14))

        // when
        val actual = blackStone.findOmok()
        val expected = false

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
