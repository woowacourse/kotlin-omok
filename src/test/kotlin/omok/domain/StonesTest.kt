package omok.domain

import omok.fixture.BLACK_A1
import omok.fixture.BLACK_A2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val stones = Stones(listOf(BLACK_A1, BLACK_A2))
        val expected = Point(0, 1)
        assertThat(stones.lastStonePoint()).isEqualTo(expected)
    }
}
