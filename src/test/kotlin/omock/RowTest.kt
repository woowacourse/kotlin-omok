package omock

import omock.model.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RowTest {
    @Test
    fun `comma의 범위는 1~ 15의 정수이다`() {
        val row = Row("1")
        assertThat(row.comma).isEqualTo("1")
    }

    @Test
    fun `comma의 범위가 1~ 15의 정수가 아닌 경우 예외를 발생시킨다`() {
        assertThrows<IllegalArgumentException> {
            Row("16")
        }
    }
}
