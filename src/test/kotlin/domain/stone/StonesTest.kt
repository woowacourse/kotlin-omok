package domain.stone

import domain.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `각각 다른 위치의 오목알을 가질 수 있다`() {
        assertDoesNotThrow {
            Stones(Stone.of(1, 1), Stone.of(1, 2))
        }
    }

    @Test
    fun `중복되는 위치의 오목알을 가지면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Stones(Stone.of(1, 1), Stone.of(1, 1))
        }
    }

    @Test
    fun `오목알을 놓을 수 있다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2))
        val newStones = stones.add(Stone.of(1, 3))

        assertThat(newStones).isEqualTo(Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3)))
    }

    @Test
    fun `오목알이 포함되어 있는지 판단한다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3))
        val expected = stones.hasStone(Stone.of(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `오목알이 5개 이상 연이어 있으면 참을 반환한다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3), Stone.of(1, 4), Stone.of(1, 5))
        val expected = stones.checkWin(Stone.of(1, 1))

        assertThat(expected).isTrue
    }

    @Test
    fun `오목알이 5개 미만 연이어 있으면 거짓을 반환한다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3))
        val expected = stones.checkWin(Stone.of(1, 1))

        assertThat(expected).isFalse
    }

    @Test
    fun `마지막 놓은 오목알을 반환한다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3))
        val expected = stones.lastStone

        assertThat(expected).isEqualTo(Stone.of(1, 3))
    }

    @Test
    fun `오목알이 놓인 위치 목록을 반환한다`() {
        val stones = Stones(Stone.of(1, 1), Stone.of(1, 2), Stone.of(1, 3))
        val expected = stones.getPositions()
        val actual = listOf(Position(1, 1), Position(1, 2), Position(1, 3))

        assertThat(expected).isEqualTo(actual)
    }
}
