package omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `현 위치의 북쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.I, Row.TEN)

        val actual = position.getNorth()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 북쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.I, Row.FIFTEEN)

        val actual = position.getNorth()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 북동쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.TEN)

        val actual = position.getNorthEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 북동쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.O, Row.FIFTEEN)

        val actual = position.getNorthEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 동쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.NINE)

        val actual = position.getEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 동쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.O, Row.NINE)

        val actual = position.getEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 남동쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.J, Row.EIGHT)

        val actual = position.getSouthEast()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 남동쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.I, Row.ONE)

        val actual = position.getSouthEast()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 남쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.I, Row.EIGHT)

        val actual = position.getSouth()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 남쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.I, Row.ONE)

        val actual = position.getSouth()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 남서쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.EIGHT)

        val actual = position.getSouthWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 남서쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.A, Row.ONE)

        val actual = position.getSouthWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 서쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.NINE)

        val actual = position.getWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 서쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.A, Row.NINE)

        val actual = position.getWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `현 위치의 북서쪽 좌표가 존재한다`() {
        val position = Position(Column.I, Row.NINE)
        val expected = Position(Column.H, Row.TEN)

        val actual = position.getNorthWest()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현 위치의 북서쪽 좌표가 존재하지 않는다`() {
        val position = Position(Column.A, Row.FIFTEEN)

        val actual = position.getNorthWest()

        assertThat(actual).isNull()
    }

    @Test
    fun `좌표값이 존재한다`() {
        val positionText = "I9"

        assertThat(positionText.toPosition()).isEqualTo(Position(Column.I, Row.NINE))
    }

    @Test
    fun `좌표값이 존재하지 않는다`() {
        val positionText = "pingu"

        assertThrows<IllegalArgumentException> { positionText.toPosition() }
    }
}
