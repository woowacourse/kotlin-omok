package woowacourse.omok.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor.BLACK
import woowacourse.omok.model.stone.StoneColor.WHITE
import woowacourse.omok.model.stone.Stones

class StonesTest {
    @Test
    fun `돌을 추가할 수 있다`() {
        val stones = Stones()
        stones.addLastStone(Stone(1, 1, BLACK))

        val actual = stones.stones.size

        val expected = 1

        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `마지막으로 둔 돌을 확인할 수 있다`() {
        val stones = Stones()
        stones.addLastStone(Stone(8, 8, BLACK))

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

    @Test
    fun `이번 차례에 두어야 할 돌의 색깔을 알 수 있다`() {
        val stones =
            Stones(
                stones =
                    setOf(
                        Stone(8, 8, BLACK),
                    ),
                lastStone = Stone(8, 8, BLACK),
            )
        val actual = stones.currentStoneColor()

        val expected = WHITE

        assertThat(actual).isEqualTo(expected)
    }
}
