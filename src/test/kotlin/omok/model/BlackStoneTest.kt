package omok.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlackStoneTest {
    @Test
    fun `흑돌은 흑색이다`() {
        val position = Position(X("A"), Y(1))
        val actual = BlackStone(position).color()

        Assertions.assertThat(actual).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `흑돌은 좌표를 가진다`() {
        val position = Position(X("A"), Y(1))
        val actual = BlackStone(position).position()

        Assertions.assertThat(actual).isEqualTo(position)
    }
}
