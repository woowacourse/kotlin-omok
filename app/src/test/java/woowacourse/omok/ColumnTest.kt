package omock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.position.Column

class ColumnTest {
    @Test
    fun `comma의 범위는 A ~ O이다`() {
        val row = Column("A")
        assertThat(row.comma).isEqualTo("A")
    }

    @Test
    fun `comma의 범위가 A ~ O가 아닌 경우 예외를 발생시킨다`() {
        assertThrows<IllegalArgumentException> {
            Column("P")
        }
    }
}
