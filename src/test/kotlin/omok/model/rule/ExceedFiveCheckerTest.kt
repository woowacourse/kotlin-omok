package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.StoneType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExceedFiveCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = StoneType.NONE
            }
        }
    }

    @Test
    fun `장목 케이스`() {
        val blackStone = BlackStone()
        blackStone.putStone(Position.of('C', 10))
        blackStone.putStone(Position.of('C', 11))
        blackStone.putStone(Position.of('C', 12))
        blackStone.putStone(Position.of('C', 14))
        blackStone.putStone(Position.of('C', 15))

        val actual = ExceedFiveChecker.checkMoreThanFive(2, 12)
        Assertions.assertThat(actual).isEqualTo(true)
    }
}
