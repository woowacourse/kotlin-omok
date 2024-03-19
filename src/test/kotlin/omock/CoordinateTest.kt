package omock

import omock.model.Column
import omock.model.Coordinate
import omock.model.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CoordinateTest {
    @Test
    fun `좌표는 row, column를 가지고 있다`() {
        val coordinate = Coordinate(row = Row("1"), column = Column("A"))

        assertThat(coordinate.row.comma).isEqualTo("1")
        assertThat(coordinate.column.comma).isEqualTo("A")
    }

    @Test
    fun `row, column을 통해 Coordinate를 재활용 할 수 있다`() {
        val row = Row("1")
        val column = Column("A")
        val actual = Coordinate.from(row = row, column = column)
        assertThat(actual.row).isEqualTo(row)
        assertThat(actual.column).isEqualTo(column)
    }
}
