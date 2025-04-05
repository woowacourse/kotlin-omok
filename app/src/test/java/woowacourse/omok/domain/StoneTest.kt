
package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class StoneTest {
    @Test
    fun `오목돌은 row 위치를 가진다`() {
        val stone = Stone(Position(0, 0), StoneType.BLACK)
        assertThat(stone.position.x).isEqualTo(0)
    }

    @Test
    fun `오목돌은 col 위치를 가진다`() {
        val stone = Stone(Position(0, 0), StoneType.BLACK)
        assertThat(stone.position.y).isEqualTo(0)
    }

    @Test
    fun `오목돌은 색상을 가진다`() {
        val stone = Stone(Position(0, 0), StoneType.WHITE)
    }
}
