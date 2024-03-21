package omok.model

import omok.model.rule.ban.DoubleFourForbiddenPlace
import omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import omok.model.rule.ban.OverlineForbiddenPlace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    private val blackStonePlayer = Player(
        Stone.BLACK, listOf(
            DoubleFourForbiddenPlace(),
            DoubleOpenThreeForbiddenPlace(),
            OverlineForbiddenPlace()
        )
    )
    private val whiteStonePlayer = Player(Stone.WHITE)

    @Test
    fun `검정 돌은 3-3인 경우에 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(5, 5), Stone.BLACK),
            StonePosition(Position(8, 5), Stone.BLACK),
            StonePosition(Position(7, 7), Stone.BLACK),
            StonePosition(Position(7, 8), Stone.BLACK),
        )

        assertThat(blackStonePlayer.canPlace(board, (Position(7, 5)))).isFalse
    }

    @Test
    fun `검정 돌은 4-4인 경우에 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(3, 9), Stone.BLACK),
            StonePosition(Position(6, 9), Stone.BLACK),
            StonePosition(Position(7, 9), Stone.BLACK),
            StonePosition(Position(9, 9), Stone.BLACK),
        )

        assertThat(blackStonePlayer.canPlace(board, (Position(5, 9)))).isFalse
    }

    @Test
    fun `검정 돌은 장목인 경우에 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(0, 0), Stone.WHITE),
            StonePosition(Position(0, 7), Stone.WHITE),
            StonePosition(Position(0, 1), Stone.BLACK),
            StonePosition(Position(0, 2), Stone.BLACK),
            StonePosition(Position(0, 4), Stone.BLACK),
            StonePosition(Position(0, 5), Stone.BLACK),
            StonePosition(Position(0, 6), Stone.BLACK),
        )

        assertThat(blackStonePlayer.canPlace(board, (Position(0, 3)))).isFalse
    }

    @Test
    fun `하얀 돌은 4-4인 경우에 놓을 수 있다`() {
        val board = initBoard(
            StonePosition(Position(3, 9), Stone.WHITE),
            StonePosition(Position(6, 9), Stone.WHITE),
            StonePosition(Position(7, 9), Stone.WHITE),
            StonePosition(Position(9, 9), Stone.WHITE),
        )

        assertThat(whiteStonePlayer.canPlace(board, (Position(5, 9)))).isTrue
    }

    @Test
    fun `하얀 돌은 장목인 경우에 놓을 수 있다`() {
        val board = initBoard(
            StonePosition(Position(0, 1), Stone.WHITE),
            StonePosition(Position(0, 2), Stone.WHITE),
            StonePosition(Position(0, 4), Stone.WHITE),
            StonePosition(Position(0, 5), Stone.WHITE),
            StonePosition(Position(0, 6), Stone.WHITE),
        )
        assertThat(whiteStonePlayer.canPlace(board, (Position(0, 3)))).isTrue
    }
}
