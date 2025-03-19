package omok.domain

import omok.fixture.A1
import omok.fixture.A2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val stones = Stones(setOf(A1, A2), StoneColor.BLACK)
        val expected = A2
        assertThat(stones.lastStonePoint()).isEqualTo(expected)
    }

//    @Test
//    fun `가로로 돌이 5개 놓이면 승리한다`() {
//        val stones = Stones(setOf(B1, C1, D1, E1, F1), StoneColor.BLACK)
//        assertThat(stones.isOmok()).isTrue()
//    }
//
//    @Test
//    fun `세로로 돌이 5개 놓이면 승리한다`() {
//        val stones = Stones(setOf(A1, A2, A3, A4, A5), StoneColor.BLACK)
//        assertThat(stones.isOmok()).isTrue()
//    }
//
//    @Test
//    fun `오른쪽 위 대각선으로 돌이 5개 놓이면 승리한다`() {
//        val stones = Stones(setOf(E5, F6, G7, H8, I9), StoneColor.BLACK)
//        assertThat(stones.isOmok()).isTrue()
//    }
//
//    @Test
//    fun `오른쪽 아래 대각선으로 돌이 5개 놓이면 승리한다`() {
//        val stones = Stones(setOf(E10, F9, G8, H7, I6), StoneColor.BLACK)
//        assertThat(stones.isOmok()).isTrue()
//    }
}
