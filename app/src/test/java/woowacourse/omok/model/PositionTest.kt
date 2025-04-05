package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.model.position.GridElement
import woowacourse.omok.model.position.Position

class PositionTest {
    @Test
    fun `위치는 행과 열로 이뤄져있다`() {
        val position = Position(GridElement(5), GridElement(5))

        assertAll(
            { assertThat(position.row.value).isEqualTo(5) },
            { assertThat(position.column.value).isEqualTo(5) },
        )
    }
}
