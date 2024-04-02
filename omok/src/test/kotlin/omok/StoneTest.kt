package omok

import omok.model.Column
import omok.model.Row
import omok.model.state.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {
    @Test
    fun `돌은 row, column를 가지고 있다`() {
        val stone = Stone(row = Row("1"), column = Column("A"))

        assertThat(stone.row.comma).isEqualTo("1")
        assertThat(stone.column.comma).isEqualTo("A")
    }

    @Test
    fun `row, column을 통해 돌을 재활용 할 수 있다`() {
        val row = Row("1")
        val column = Column("A")
        val actual = Stone.from(row = row, column = column)
        assertThat(actual.row).isEqualTo(row)
        assertThat(actual.column).isEqualTo(column)
    }
}
