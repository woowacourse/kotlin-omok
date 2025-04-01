package omok.model

import omok.model.entity.position.DefaultPosition
import omok.model.testDouble.FakeGridElement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PositionTest {
    @Test
    fun `위치는 행과 열로 이뤄져있다`() {
        val position = DefaultPosition(FakeGridElement(), FakeGridElement())

        assertAll(
            { assertThat(position.row.value).isEqualTo(5) },
            { assertThat(position.column.value).isEqualTo(5) },
        )
    }
}
