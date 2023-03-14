package omok.domain.board

import omok.domain.player.Black
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.IllegalStateException

class BoardTest {
    @ParameterizedTest(name = "{0}, {1}이 보드 좌표에 존재한다")
    @CsvSource(
        "A, ONE", "A, TWO", "A, THREE", "A, FOUR", "A, FIVE", "A, SIX", "A, SEVEN",
        "A, EIGHT", "A, NINE", "A, TEN", "A, ELEVEN", "A, TWELVE", "A, THIRTEEN", "A, FOURTEEN", "A, FIFTEEN",
        "B, ONE", "B, TWO", "B, THREE", "B, FOUR", "B, FIVE", "B, SIX", "B, SEVEN",
        "B, EIGHT", "B, NINE", "B, TEN", "B, ELEVEN", "B, TWELVE", "B, THIRTEEN", "B, FOURTEEN", "B, FIFTEEN",
        "C, ONE", "C, TWO", "C, THREE", "C, FOUR", "C, FIVE", "C, SIX", "C, SEVEN",
        "C, EIGHT", "C, NINE", "C, TEN", "C, ELEVEN", "C, TWELVE", "C, THIRTEEN", "C, FOURTEEN", "C, FIFTEEN",
        "D, ONE", "D, TWO", "D, THREE", "D, FOUR", "D, FIVE", "D, SIX", "D, SEVEN",
        "D, EIGHT", "D, NINE", "D, TEN", "D, ELEVEN", "D, TWELVE", "D, THIRTEEN", "D, FOURTEEN", "D, FIFTEEN",
        "E, ONE", "E, TWO", "E, THREE", "E, FOUR", "E, FIVE", "E, SIX", "E, SEVEN",
        "E, EIGHT", "E, NINE", "E, TEN", "E, ELEVEN", "E, TWELVE", "E, THIRTEEN", "E, FOURTEEN", "E, FIFTEEN",
        "F, ONE", "F, TWO", "F, THREE", "F, FOUR", "F, FIVE", "F, SIX", "F, SEVEN",
        "F, EIGHT", "F, NINE", "F, TEN", "F, ELEVEN", "F, TWELVE", "F, THIRTEEN", "F, FOURTEEN", "F, FIFTEEN",
        "G, ONE", "G, TWO", "G, THREE", "G, FOUR", "G, FIVE", "G, SIX", "G, SEVEN",
        "G, EIGHT", "G, NINE", "G, TEN", "G, ELEVEN", "G, TWELVE", "G, THIRTEEN", "G, FOURTEEN", "G, FIFTEEN",
        "H, ONE", "H, TWO", "H, THREE", "H, FOUR", "H, FIVE", "H, SIX", "H, SEVEN",
        "H, EIGHT", "H, NINE", "H, TEN", "H, ELEVEN", "H, TWELVE", "H, THIRTEEN", "H, FOURTEEN", "H, FIFTEEN",
        "I, ONE", "I, TWO", "I, THREE", "I, FOUR", "I, FIVE", "I, SIX", "I, SEVEN",
        "I, EIGHT", "I, NINE", "I, TEN", "I, ELEVEN", "I, TWELVE", "I, THIRTEEN", "I, FOURTEEN", "I, FIFTEEN",
        "J, ONE", "J, TWO", "J, THREE", "J, FOUR", "J, FIVE", "J, SIX", "J, SEVEN",
        "J, EIGHT", "J, NINE", "J, TEN", "J, ELEVEN", "J, TWELVE", "J, THIRTEEN", "J, FOURTEEN", "J, FIFTEEN",
        "K, ONE", "K, TWO", "K, THREE", "K, FOUR", "K, FIVE", "K, SIX", "K, SEVEN",
        "K, EIGHT", "K, NINE", "K, TEN", "K, ELEVEN", "K, TWELVE", "K, THIRTEEN", "K, FOURTEEN", "K, FIFTEEN",
        "L, ONE", "L, TWO", "L, THREE", "L, FOUR", "L, FIVE", "L, SIX", "L, SEVEN",
        "L, EIGHT", "L, NINE", "L, TEN", "L, ELEVEN", "L, TWELVE", "L, THIRTEEN", "L, FOURTEEN", "L, FIFTEEN",
        "M, ONE", "M, TWO", "M, THREE", "M, FOUR", "M, FIVE", "M, SIX", "M, SEVEN",
        "M, EIGHT", "M, NINE", "M, TEN", "M, ELEVEN", "M, TWELVE", "M, THIRTEEN", "M, FOURTEEN", "M, FIFTEEN",
        "N, ONE", "N, TWO", "N, THREE", "N, FOUR", "N, FIVE", "N, SIX", "N, SEVEN",
        "N, EIGHT", "N, NINE", "N, TEN", "N, ELEVEN", "N, TWELVE", "N, THIRTEEN", "N, FOURTEEN", "N, FIFTEEN",
        "O, ONE", "O, TWO", "O, THREE", "O, FOUR", "O, FIVE", "O, SIX", "O, SEVEN",
        "O, EIGHT", "O, NINE", "O, TEN", "O, ELEVEN", "O, TWELVE", "O, THIRTEEN", "O, FOURTEEN", "O, FIFTEEN"
    )
    fun `1 ~ 15의 row 와 A ~ O의 column 으로 이루어진 좌표들을 가진다`(column: Column, row: Row) {
        val position = Position(Column.A, Row.ONE)

        assertThat(Board.POSITIONS).contains(position)
    }

    @Test
    fun `해당 좌표에 돌이 없다면 비어있다`() {
        val position = Position(Column.A, Row.ONE)

        assertThat(Board().isEmpty(position)).isTrue
    }

    @Test
    fun `해당 좌표에 돌이 있으면 비어있지 않다`() {
        val position = Position(Column.E, Row.FIVE)

        val board = Board(DOWNWARD_DIAGONAL_BOARD)

        assertThat(board.isEmpty(position)).isFalse
    }

    @Test
    fun `돌을 놓으려는 좌표에 이미 돌이 존재하면 놓을 수 없다`() {
        val board = Board(DOWNWARD_DIAGONAL_BOARD)

        assertThrows<IllegalStateException> { board.place(Position(Column.J, Row.TEN), Black) }
    }

    @Test
    fun `돌을 놓으면, 해당 위치는 비어있지 않다`() {
        val board = Board()

        board.place(Position(Column.J, Row.TEN), Black)

        assertThat(board.isEmpty(Position(Column.J, Row.TEN))).isFalse
    }
}
