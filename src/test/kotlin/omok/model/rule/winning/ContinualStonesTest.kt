package omok.model.rule.winning

import omok.model.Position
import omok.model.Stone
import omok.model.StonePosition
import omok.model.initBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContinualStonesTest {
    @Test
    fun `가로로 연속된 돌의 개수를 센다`() {
        val board =
            initBoard(
                StonePosition(Position(0, 0), Stone.WHITE),
                StonePosition(Position(0, 7), Stone.WHITE),
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
                StonePosition(Position(0, 6), Stone.BLACK),
                StonePosition(Position(0, 3), Stone.BLACK),
            )

        val actual = ContinualStones.count(board, Position(0, 3))
        assertThat(actual).isEqualTo(6)
    }

    @Test
    fun `대각선로 연속된 돌의 개수를 센다`() {
        val board =
            initBoard(
                StonePosition(Position(1, 1), Stone.BLACK),
                StonePosition(Position(2, 2), Stone.BLACK),
                StonePosition(Position(3, 3), Stone.BLACK),
                StonePosition(Position(4, 4), Stone.BLACK),
                StonePosition(Position(5, 5), Stone.BLACK),
                StonePosition(Position(6, 6), Stone.BLACK),
            )

        val actual = ContinualStones.count(board, Position(6, 6))
        assertThat(actual).isEqualTo(6)
    }
}
