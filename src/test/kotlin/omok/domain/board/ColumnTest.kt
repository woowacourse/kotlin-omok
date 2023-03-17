package omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ColumnTest {
    @Test
    fun `H의 오른쪽 컬럼은 I다`() {
        val column = Column.H

        assertThat(column.right()).isEqualTo(Column.I)
    }

    @Test
    fun `O의 오른쪽 컬럼은 존재하지 않는다`() {
        val column = Column.O

        assertThat(column.right()).isNull()
    }

    @Test
    fun `H의 왼쪽 컬럼은 G이다`() {
        val column = Column.H

        assertThat(column.left()).isEqualTo(Column.G)
    }

    @Test
    fun `A의 왼쪽 컬럼은 존재하지 않는다`() {
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
