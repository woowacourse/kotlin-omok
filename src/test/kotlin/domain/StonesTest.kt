package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StonesTest {

    @Test
    fun `스톤들의 정보를 담고 있다`() {
        val stonePosition = StonePosition.from(1, 1)!!
        val stoneType = StoneType.BLACK
        val stones = TestStones(
            Stone(stonePosition, stoneType),
        )
        assertAll(
            { assertThat(stones.values[0].position).isEqualTo(stonePosition) },
            { assertThat(stones.values[0].type).isEqualTo(stoneType) },
        )
    }

    @Test
    fun `스톤을 받아 추가한다`() {
        val stonePosition = StonePosition.from(1, 1)!!
        val stoneType = StoneType.BLACK
        val stones = TestStones()
        stones.add(Stone(stonePosition, stoneType))
        assertAll(
            { assertThat(stones.values[0].position).isEqualTo(stonePosition) },
            { assertThat(stones.values[0].type).isEqualTo(stoneType) },
        )
    }

    class TestStones(vararg stones: Stone) : Stones(*stones)
}
