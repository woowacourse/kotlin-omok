package omok.domain

import omok.fixture.BLACK_A1
import omok.fixture.BLACK_A2
import omok.fixture.BLACK_A3
import omok.fixture.BLACK_A4
import omok.fixture.BLACK_A5
import omok.fixture.BLACK_B1
import omok.fixture.BLACK_C1
import omok.fixture.BLACK_D1
import omok.fixture.BLACK_E1
import omok.fixture.BLACK_E10
import omok.fixture.BLACK_E5
import omok.fixture.BLACK_F1
import omok.fixture.BLACK_F6
import omok.fixture.BLACK_F9
import omok.fixture.BLACK_G7
import omok.fixture.BLACK_G8
import omok.fixture.BLACK_H7
import omok.fixture.BLACK_H8
import omok.fixture.BLACK_I6
import omok.fixture.BLACK_I9
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val stones = Stones(listOf(BLACK_A1, BLACK_A2))
        val expected = Point(0, 1)
        assertThat(stones.lastStonePoint()).isEqualTo(expected)
    }

    @Test
    fun `가로로 돌이 5개 놓이면 승리한다`() {
        val stones = Stones(listOf(BLACK_B1, BLACK_C1, BLACK_D1, BLACK_E1, BLACK_F1))
        assertThat(stones.isOmok()).isTrue()
    }

    @Test
    fun `세로로 돌이 5개 놓이면 승리한다`() {
        val stones = Stones(listOf(BLACK_A1, BLACK_A2, BLACK_A3, BLACK_A4, BLACK_A5))
        assertThat(stones.isOmok()).isTrue()
    }

    @Test
    fun `오른쪽 위 대각선으로 돌이 5개 놓이면 승리한다`() {
        val stones = Stones(listOf(BLACK_E5, BLACK_F6, BLACK_G7, BLACK_H8, BLACK_I9))
        assertThat(stones.isOmok()).isTrue()
    }

    @Test
    fun `오른쪽 아래 대각선으로 돌이 5개 놓이면 승리한다`() {
        val stones = Stones(listOf(BLACK_E10, BLACK_F9, BLACK_G8, BLACK_H7, BLACK_I6))
        assertThat(stones.isOmok()).isTrue()
    }
}
