package omok.model.rule

import omok.model.board.Board
import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExceedFiveCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
    }

    @Test
    fun `장목 케이스`() {
        val blackStone = BlackStone()
        blackStone.putStone(Position(Row('C'), Col.from(10)))
        blackStone.putStone(Position(Row('C'), Col.from(11)))
        blackStone.putStone(Position(Row('C'), Col.from(12)))
        blackStone.putStone(Position(Row('C'), Col.from(14)))
        blackStone.putStone(Position(Row('C'), Col.from(15)))

        val actual = ExceedFiveChecker.isMoreThanFive(Position(Row('C'), Col.from(13)))
        Assertions.assertThat(actual).isEqualTo(true)
    }
}
