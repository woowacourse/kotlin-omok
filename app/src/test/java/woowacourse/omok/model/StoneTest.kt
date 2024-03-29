package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.model.Color
import woowacourse.omok.model.Column
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.Row
import woowacourse.omok.model.Stone

class StoneTest {
    @ParameterizedTest
    @CsvSource("1,1", "13,6", "4,7")
    fun `돌은 위치와 색상을 가지고 있다`(
        row: Int,
        col: Int,
    ) {
        val coordinate = Coordinate(Row(row), Column(col))
        val stone = Stone(Color.BLACK, coordinate)

        assertThat(stone.color).isEqualTo(Color.BLACK)
        assertThat(stone.coordinate).isEqualTo(coordinate)
    }
}
