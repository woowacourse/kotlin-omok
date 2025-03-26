package omok.model.stone

import omok.model.stone.StoneColor.BLACK
import omok.model.stone.StoneColor.WHITE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `돌을 추가할 수 있다`() {
        val stones = Stones()
        stones.add(Stone(1, 1, BLACK))

        val actual = stones.stones.size

        val expected = 1

        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `마지막으로 둔 돌을 설정할 수 있다`() {
        val stones = Stones()
        stones.setLastStone(Stone(8, 8, BLACK))

        val actual = stones.lastStone

        val expected = Stone(8, 8, BLACK)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 돌이 있는 위치인지 확인할 수 있다`() {
        val stones =
            Stones(
                setOf(
                    Stone(8, 8, BLACK),
                ),
            )
        val actual = stones.isOccupied(Stone(8, 8, WHITE))

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }
}
