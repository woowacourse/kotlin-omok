package omok.domain

import omok.domain.stones.BlackStones
import omok.fixture.A1
import omok.fixture.A2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val stones = BlackStones(setOf(A1, A2))
        val expected = A2
        assertThat(stones.lastStonePoint()).isEqualTo(expected)
    }
}
