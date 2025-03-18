package study

import domain.model.Column
import domain.model.Position
import domain.model.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MapTest {
    @Test
    fun `데이터 클래스 key로 value 탐색`() {
        val position = Position(Column.from('A'), Row(1))
        val map = mapOf(position to 3)
        assertThat(map[Position(Column.from('A'), Row(1))]).isEqualTo(3)
    }
}
