package omok.domain.judgment

import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import omok.domain.player.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ThreeThreeRefereeTest {
    @ParameterizedTest
    @CsvSource("D, TWELVE", "E, THREE", "L, ELEVEN", "K, FOUR")
    fun `3-3이면 금지된 수이다`(column: Column, row: Row) {
        val board = THREE_THREE_BOARD
        val referee = ThreeThreeReferee(Stone.BLACK)
        val position = Position(column, row)

        board[position] = Stone.BLACK

        assertThat(referee.isForbiddenPlacement(board, position)).isTrue
    }

    @ParameterizedTest
    @CsvSource("D, ELEVEN", "D, FIFTEEN", "M, ELEVEN", "O, FOUR")
    fun `3-3이 아니면 금지된 수가 아니다`(column: Column, row: Row) {
        val board = THREE_THREE_BOARD
        val referee = ThreeThreeReferee(Stone.BLACK)
        val position = Position(column, row)

        board[position] = Stone.BLACK

        assertThat(referee.isForbiddenPlacement(board, position)).isFalse
    }

    @Test
    fun `4-3이면 금지된 수가 아니다`() {
        val board = FOUR_THREE_BOARD
        val referee = ThreeThreeReferee(Stone.BLACK)
        val position = Position(Column.D, Row.TWELVE)

        board[position] = Stone.BLACK

        assertThat(referee.isForbiddenPlacement(board, position)).isFalse
    }

    @Test
    fun `4-3-3이면 금지된 수이다`() {
        val board = FOUR_THREE_THREE_BOARD
        val referee = ThreeThreeReferee(Stone.BLACK)
        val position = Position(Column.D, Row.TWELVE)

        board[position] = Stone.BLACK

        assertThat(referee.isForbiddenPlacement(board, position)).isTrue
    }
}
