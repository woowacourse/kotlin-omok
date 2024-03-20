package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RuleTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `같은 타입의 돌이 5개 이상 가로로 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 5)))

        repeat(5) {
            assertThat(Rule.isWinCondition(board.board, Stone(StoneType.BLACK, Point(1, it + 1)))).isTrue
        }
    }

    @Test
    fun `같은 타입의 돌이 5개 이상 세로로 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 1)))

        repeat(5) {
            assertThat(Rule.isWinCondition(board.board, Stone(StoneType.BLACK, Point(it + 1, 1)))).isTrue
        }
    }

    @Test
    fun `같은 타입의 돌이 5개 이상 우상향 대각선으로 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))

        repeat(5) {
            assertThat(Rule.isWinCondition(board.board, Stone(StoneType.BLACK, Point(it + 1, it + 1)))).isTrue
        }
    }

    @Test
    fun `같은 타입의 돌이 5개 이상 우하향 대각선으로 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))
        board.putStone(Stone(StoneType.BLACK, Point(6, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(7, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(8, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(9, 1)))

        repeat(5) {
            assertThat(Rule.isWinCondition(board.board, Stone(StoneType.BLACK, Point(5 + it, 5 - it)))).isTrue
        }
    }
}
