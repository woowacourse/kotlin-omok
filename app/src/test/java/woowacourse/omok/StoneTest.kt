package omock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.stone.Stone

class StoneTest {
    @Test
    fun `돌은 row, column를 가지고 있다`() {
        val stone = Stone(row = Row("1"), column = Column("A"))

        assertThat(stone.getRowComma()).isEqualTo("1")
        assertThat(stone.getColumnComma()).isEqualTo("A")
    }

    @Test
    fun `돌을 통해서 Board의 인덱스를 얻을 수 있다`() {
        val row = Row("1")
        val column = Column("A")
        val actual = Stone.from(row = row, column = column)

        assertThat(actual.getBoardRowIndex()).isEqualTo(Row.MAX_ROW)
        assertThat(actual.getColumnIndex()).isEqualTo(Column.MIN_COLUMN_INDEX)
    }
}
