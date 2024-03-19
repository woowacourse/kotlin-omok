package omok.model

import fixture.FIRST_ROW_FIRST_COL
import fixture.FIRST_ROW_SECOND_COL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `stone 을 하나 추가할 경우, Stones 의 사이즈는 1 증가한다`() {
        // when
        val stones = Stones()
        assertThat(stones.stones.size).isEqualTo(0)

        // given
        stones.add(FIRST_ROW_FIRST_COL)

        // then
        assertThat(stones.stones.size).isEqualTo(1)
    }

    @Test
    fun `Stones 의 마지막 스톤을 확인할 수 있다`() {
        val stones = Stones()
        stones.add(FIRST_ROW_FIRST_COL)
        stones.add(FIRST_ROW_SECOND_COL)

        assertThat(stones.lastStone()).isEqualTo(FIRST_ROW_SECOND_COL)
    }
}
