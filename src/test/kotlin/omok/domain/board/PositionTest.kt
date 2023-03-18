package omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `I9 위치의 북쪽 좌표는 I10 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.I, Row.TEN)

        val actual = position.getNorth()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `I15 위치의 북쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.I, Row.FIFTEEN)

        val actual = position.getNorth()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 북동쪽 좌표는 J10이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.TEN)

        val actual = position.getNorthEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `O15 위치의 북동쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.O, Row.FIFTEEN)

        val actual = position.getNorthEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 동쪽 좌표는 J9 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.NINE)

        val actual = position.getEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `O9 위치의 동쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.O, Row.NINE)

        val actual = position.getEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 남동쪽 좌표는 J8 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.EIGHT)

        val actual = position.getSouthEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `I1 위치의 남동쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.I, Row.ONE)

        val actual = position.getSouthEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 남쪽 좌표는 I8 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.I, Row.EIGHT)

        val actual = position.getSouth()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `I1 위치의 남쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.I, Row.ONE)

        val actual = position.getSouth()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 남서쪽 좌표는 H8 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.EIGHT)

        val actual = position.getSouthWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A1 위치의 남서쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.A, Row.ONE)

        val actual = position.getSouthWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 서쪽 좌표는 H9 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.NINE)

        val actual = position.getWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A9 위치의 서쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.A, Row.NINE)

        val actual = position.getWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `I9 위치의 북서쪽 좌표는 H10 이다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.TEN)

        val actual = position.getNorthWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A15 위치의 북서쪽 좌표는 존재하지 않는다`() {
        val position = Position(Column.A, Row.FIFTEEN)

        val actual = position.getNorthWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `좌표값이 존재한다`() {
        val positionText = Pair(9, 9)

        assertThat(positionText.toPosition()).isEqualTo(Position(Column.I, Row.NINE))
    }
}
