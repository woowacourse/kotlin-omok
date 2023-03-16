package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StonesTest {

    @Test
    fun `스톤들의 정보를 담고 있다`() {
        val stonePosition = StonePosition.from(1, 1)!!
        val stoneType = StoneType.BLACK
        val stones = Stones(
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
        val stones = Stones()
        stones.add(Stone(stonePosition, stoneType))
        assertAll(
            { assertThat(stones.values[0].position).isEqualTo(stonePosition) },
            { assertThat(stones.values[0].type).isEqualTo(stoneType) },
        )
    }

    @Test
    fun `스톤을 받아 해당 스톤의 위치에 돌이 놓여있는지 확인`() {
        val stones = Stones()
        stones.add(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))

        val stone = Stone(StonePosition.from(1, 1)!!, StoneType.BLACK)

        assertThat(stones.containsPosition(stone)).isTrue
    }

    @Test
    fun `스톤들을 받아서 두 스톤들을 더한 값을 반환한다`() {
        val blackStones = Stones()
        val whiteStones = Stones()
        blackStones.add(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        whiteStones.add(Stone(StonePosition.from(2, 2)!!, StoneType.WHITE))

        val stones = blackStones + whiteStones

        assertThat(stones.values.size).isEqualTo(2)
    }
}
