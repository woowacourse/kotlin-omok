package omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ColumnTest {
    @Test
    fun `오른쪽 컬럼이 존재한다`() {
        val column = Column.H

        assertThat(column.right()).isEqualTo(Column.I)
    }

    @Test
    fun `오른쪽 컬럼이 존재하지 않는다`() {
        val column = Column.O

        assertThat(column.right()).isNull()
    }

    @Test
    fun `왼쪽 컬럼이 존재한다`() {
        val column = Column.H

        assertThat(column.left()).isEqualTo(Column.G)
    }

    @Test
    fun `왼쪽 컬럼이 존재하지 않는다`() {
        val column = Column.A

        assertThat(column.left()).isNull()
    }

    @Test
    fun `컬럼이 존재한다`() {
        val columnText = "H"

        assertThat(columnText.toColumn()).isEqualTo(Column.H)
    }

    @Test
    fun `컬럼이 존재하지 않는다`() {
        val columnText = "P"

        assertThat(columnText.toColumn()).isNull()
    }
}
