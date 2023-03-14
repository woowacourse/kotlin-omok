package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoordinateTest {
    @Test
    fun `좌표 생성시 오목판 밖에서 생성하면 null을 반환한다`() {
        // given
        val coordinate = Coordinate.from(16, 16)

        // when

        // then
        assertThat(coordinate).isNull()
    }

    @Test
    fun `좌표 생성시 오목판 안에서 생성하면 좌표 객체를 생성한다`() {
        // given
        val coordinate = Coordinate.from(15, 15)

        // when

        // then
        assertThat(coordinate).isNotNull
    }
}
