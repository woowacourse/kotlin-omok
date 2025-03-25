package omok.model.board

import omok.model.stone.StoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `초기 생성된 점에는 돌을 놓을 수 있는 상태이다`() {
        val position = Position.from(1, 1)

        val actual = position.canPlace()

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `점은 돌을 놓을 수 있는 지 유무를 알려준다`() {
        val position = Position.from(1, 2)

        val actual = position.canPlace()

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `점에 돌이 없으면, 흑돌을 점에 놓을 수 있다`() {
        val position = Position.from(1, 2)

        val actual = position.placeStone(StoneState.BLACK).stoneState()

        val expected = StoneState.BLACK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `점에 돌이 없으면, 백돌을 점에 놓을 수 있다`() {
        val position = Position.from(1, 2)

        val actual = position.placeStone(StoneState.WHITE).stoneState()

        val expected = StoneState.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `점에 돌이 있을 때, 돌을 놓으면 에러를 발생한다`() {
        val position = Position.from(1, 2).placeStone(StoneState.BLACK)

        assertThrows<IllegalArgumentException> { position.placeStone(StoneState.BLACK) }
    }
}
