package domain

import error.CoordinateResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoordinateTest {
    @Test
    fun `좌표 생성시 15x15 오목판 밖에서 생성하면 에러를 반환한다`() {
        // given
        val coordinate = Coordinate.from(15, 15)

        // when

        // then
        assertThat(coordinate).isNotInstanceOf(CoordinateResult.Success::class.java)
    }

    @Test
    fun `좌표 생성시 15x15 오목판 안에서 생성하면 좌표 객체를 생성한다`() {
        // given
        val coordinate = Coordinate.from(14, 14)

        // when

        // then
        assertThat(coordinate).isInstanceOf(CoordinateResult.Success::class.java)
    }
}
