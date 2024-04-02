package omok.model.position

import omok.model.result.PutResult
import omok.model.rule.X_A
import omok.model.rule.Y_1
import omok.model.rule.Y_2
import omok.model.stone.BlackStone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓는다면 Failure를 반환한다`() {
        val stone = BlackStone()
        val position = Position(X_A, Y_1)
        stone.putStone(position)
        val actual = position.validatePosition()
        assertThat(actual).isEqualTo(PutResult.Failure)
    }

    @Test
    fun `돌이 놓이지 않은 자리에 돌을 놓는다면 Running을 반환한다`() {
        val position = Position(X_A, Y_2)
        val actual = position.validatePosition()
        assertThat(actual).isEqualTo(PutResult.Running)
    }
}
