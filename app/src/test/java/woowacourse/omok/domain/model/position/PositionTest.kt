package omok.domain.model.position

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.position.Position

class PositionTest {
    @Test
    fun `특정 좌표값을 가진다`() {
        // Given
        val column = 1
        val row = 1

        // When
        val position = Position.of(1, 1, 15)

        // Then
        assertSoftly(position) {
            column shouldBe 1
            row shouldBe 1
        }
    }

    @Test
    fun `행 좌표가 범위를 벗어난 경우 예외가 발생한다`() {
        // Given
        val column = 1
        val row = 16
        val size = 15

        // Then
        shouldThrowExactly<IllegalArgumentException> { Position.of(column, row, size) }
            .message shouldBe "[ERROR] 위치가 오목판의 범위를 벗어났습니다."
    }
}
