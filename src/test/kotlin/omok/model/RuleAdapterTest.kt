package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RuleAdapterTest {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter

    @BeforeEach
    fun setUp() {
        board = Board(15)
        ruleAdapter = RuleAdapter(board)
    }

    @Test
    fun `블랙 타입의 돌이 가로로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(1, 2), BlackTurn())
        board.putStone(Point(1, 3), BlackTurn())
        board.putStone(Point(1, 4), BlackTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.BLACK, Point(1, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 가로로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(1, 2), BlackTurn())
        board.putStone(Point(1, 3), BlackTurn())
        board.putStone(Point(1, 4), BlackTurn())
        board.putStone(Point(1, 6), BlackTurn())

        assertThat(ruleAdapter.checkForbidden(Stone(StoneType.BLACK, Point(1, 5)))).isTrue
    }

    @Test
    fun `화이트 타입의 돌은 가로로 6개 이상 연속되어 있어도 게임 종료`() {
        board.putStone(Point(1, 1), WhiteTurn())
        board.putStone(Point(1, 2), WhiteTurn())
        board.putStone(Point(1, 3), WhiteTurn())
        board.putStone(Point(1, 4), WhiteTurn())
        board.putStone(Point(1, 6), WhiteTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.WHITE, Point(1, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 세로로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(2, 1), BlackTurn())
        board.putStone(Point(3, 1), BlackTurn())
        board.putStone(Point(4, 1), BlackTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.BLACK, Point(5, 1)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 세로로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(2, 1), BlackTurn())
        board.putStone(Point(3, 1), BlackTurn())
        board.putStone(Point(4, 1), BlackTurn())
        board.putStone(Point(6, 1), BlackTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.BLACK, Point(1, 5)))).isFalse
    }

    @Test
    fun `화이트 타입의 돌이 세로로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Point(1, 1), WhiteTurn())
        board.putStone(Point(2, 1), WhiteTurn())
        board.putStone(Point(3, 1), WhiteTurn())
        board.putStone(Point(4, 1), WhiteTurn())
        board.putStone(Point(6, 1), WhiteTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.WHITE, Point(5, 1)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 우상향 대각선으로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(2, 2), BlackTurn())
        board.putStone(Point(3, 3), BlackTurn())
        board.putStone(Point(4, 4), BlackTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.BLACK, Point(5, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 우상향 대각선으로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Point(1, 1), BlackTurn())
        board.putStone(Point(2, 2), BlackTurn())
        board.putStone(Point(3, 3), BlackTurn())
        board.putStone(Point(4, 4), BlackTurn())
        board.putStone(Point(6, 6), BlackTurn())

        assertThat(ruleAdapter.checkForbidden(Stone(StoneType.BLACK, Point(5, 5)))).isTrue
    }

    @Test
    fun `화이트 타입의 돌이 우상향 대각선으로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Point(1, 1), WhiteTurn())
        board.putStone(Point(2, 2), WhiteTurn())
        board.putStone(Point(3, 3), WhiteTurn())
        board.putStone(Point(4, 4), WhiteTurn())
        board.putStone(Point(6, 6), WhiteTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.WHITE, Point(5, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 우하향 대각선으로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Point(5, 5), BlackTurn())
        board.putStone(Point(6, 4), BlackTurn())
        board.putStone(Point(7, 3), BlackTurn())
        board.putStone(Point(8, 2), BlackTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.BLACK, Point(9, 1)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 우하향 대각선으로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Point(5, 5), BlackTurn())
        board.putStone(Point(6, 4), BlackTurn())
        board.putStone(Point(7, 3), BlackTurn())
        board.putStone(Point(8, 2), BlackTurn())
        board.putStone(Point(10, 0), BlackTurn())

        assertThat(ruleAdapter.checkForbidden(Stone(StoneType.BLACK, Point(9, 1)))).isTrue
    }

    @Test
    fun `화이트 타입의 돌이 우하향 대각선으로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Point(5, 5), WhiteTurn())
        board.putStone(Point(6, 4), WhiteTurn())
        board.putStone(Point(7, 3), WhiteTurn())
        board.putStone(Point(8, 2), WhiteTurn())
        board.putStone(Point(10, 0), WhiteTurn())

        assertThat(ruleAdapter.checkWin(Stone(StoneType.WHITE, Point(9, 1)))).isTrue
    }
}
