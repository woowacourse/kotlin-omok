package omok.domain.model.position

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.position.Column

class ColumnTest {
    @Test
    fun `열 좌표를 가진다`() {
        // Given
        val column = Column(1)

        // Then
        column.value shouldBe 1
    }
}
