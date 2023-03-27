package omok.domain.board

import omok.domain.board.Column.Companion.toColumn
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
    fun `입력으로 들어온 좌표값을 컬럼으로 바꿔준다`() {
        val inputColumn = 8

        assertThat(toColumn(inputColumn)).isEqualTo(Column.I)
    }
}
