package omok.domain.model.position

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.position.Row

class RowTest {
    @Test
    fun `행 좌표를 가진다`() {
        // Given
        val row = Row(1)

        // Then
        row.value shouldBe 1
    }
}
