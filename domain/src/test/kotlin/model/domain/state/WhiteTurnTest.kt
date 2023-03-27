package model.domain.state

import model.domain.state.black.BlackTurn
import model.domain.state.white.WhiteOmok
import model.domain.state.white.WhiteTurn
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    private val turn = WhiteTurn()
    private val board = Board.create()

    @Test
    fun `문제없이 착수 시, 백돌턴에서 흑돌턴으로 넘어간다`() {
        // given
        val location = Location(1, 2)

        // when
        val actual = turn.place(location, board)

        // then
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `33일 경우(수직 - 수평), 착수 가능하며, 흑돌턴으로 넘어간다`() {
        // given
        with(turn) {
            place(Location(1, 2), board)
            place(Location(1, 3), board)
            place(Location(2, 4), board)
            place(Location(3, 4), board)
        }

        // when
        val actual = turn.place(Location(1, 4), board)

        // then
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `33일 경우(대각교차), 착수 가능하며, 흑돌턴으로 넘어간다`() {
        // given
        with(turn) {
            place(Location(2, 2), board)
            place(Location(3, 3), board)
            place(Location(5, 3), board)
            place(Location(6, 2), board)
        }

        // when
        val actual = turn.place(Location(4, 4), board)

        // then
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `44일 경우(수직-수평), 착수 가능하며, 흑돌턴으로 넘어간다`() {
        // given
        with(turn) {
            place(Location(1, 2), board)
            place(Location(1, 3), board)
            place(Location(1, 4), board)
            place(Location(2, 5), board)
            place(Location(3, 5), board)
            place(Location(4, 5), board)
        }

        // when
        val actual = turn.place(Location(1, 5), board)

        // then
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `44일 경우(대각교차), 착수 가능하며, 흑돌턴으로 넘어간다`() {
        // given
        with(turn) {
            place(Location(2, 2), board)
            place(Location(3, 3), board)
            place(Location(4, 4), board)
            place(Location(6, 4), board)
            place(Location(7, 3), board)
            place(Location(8, 2), board)
        }

        // when
        val actual = turn.place(Location(5, 5), board)

        // then
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `백돌이 연달아 5개가 착수되었을 경우, 백돌 오목이다`() {
        // given
        with(turn) {
            place(Location(2, 2), board)
            place(Location(3, 3), board)
            place(Location(4, 4), board)
            place(Location(5, 5), board)
        }

        // when
        val actual = turn.place(Location(6, 6), board)

        // then
        Assertions.assertThat(actual).isInstanceOf(WhiteOmok::class.java)
    }

    @Test
    fun `백돌이 오목일 경우, stoneColor는 White이다`() {
        // given
        with(turn) {
            place(Location(2, 2), board)
            place(Location(3, 3), board)
            place(Location(4, 4), board)
            place(Location(5, 5), board)
        }

        // when
        val actual = turn.place(Location(6, 6), board).stoneColor

        // then
        Assertions.assertThat(actual).isEqualTo(Stone.WHITE)
    }
}
