package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import omok.fixture.FIRST_ROW_SECOND_COL
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
    fun `중복된 Point의 Stone을 add 할 경우, Stones의 사이즈는 증가하지 않는다`() {
        // when
        val stone = BLACK_A_1
        val stones = Stones()
        stones.add(stone)
        val size = stones.stones.size

        // given
        stones.add(stone)

        // when
        assertThat(stones.stones.size).isEqualTo(size)
    }

    @Test
    fun `Stones 의 마지막 스톤을 확인할 수 있다`() {
        val stones = Stones()
        stones.add(FIRST_ROW_FIRST_COL)
        stones.add(FIRST_ROW_SECOND_COL)

        assertThat(stones.lastStone()).isEqualTo(FIRST_ROW_SECOND_COL)
    }
}
