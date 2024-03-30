package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `보드에 돌 착수 확인`() {
        val stone = Stone(StoneType.BLACK, Point(3, 8))
        board.putStone(stone)

        assertThat(board.board[8][3] == StoneType.BLACK).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 가로로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 5)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(1, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 가로로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 6)))
        board.putStone(Stone(StoneType.BLACK, Point(1, 5)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(1, 5)))).isFalse
    }

    @Test
    fun `화이트 타입의 돌은 가로로 6개 이상 연속되어 있어도 게임 종료`() {
        board.putStone(Stone(StoneType.WHITE, Point(1, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(1, 2)))
        board.putStone(Stone(StoneType.WHITE, Point(1, 3)))
        board.putStone(Stone(StoneType.WHITE, Point(1, 4)))
        board.putStone(Stone(StoneType.WHITE, Point(1, 5)))
        board.putStone(Stone(StoneType.WHITE, Point(1, 6)))

        assertThat(board.isWinCondition(Stone(StoneType.WHITE, Point(1, 6)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 세로로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 1)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(5, 1)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 세로로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(6, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 1)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(1, 5)))).isFalse
    }

    @Test
    fun `화이트 타입의 돌이 세로로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Stone(StoneType.WHITE, Point(1, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(2, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(3, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(4, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(6, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(5, 1)))

        assertThat(board.isWinCondition(Stone(StoneType.WHITE, Point(5, 1)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 우상향 대각선으로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(5, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌은 우상향 대각선으로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Stone(StoneType.BLACK, Point(1, 1)))
        board.putStone(Stone(StoneType.BLACK, Point(2, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(3, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(4, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(6, 6)))
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(5, 5)))).isFalse
    }

    @Test
    fun `화이트 타입의 돌이 우상향 대각선으로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Stone(StoneType.WHITE, Point(1, 1)))
        board.putStone(Stone(StoneType.WHITE, Point(2, 2)))
        board.putStone(Stone(StoneType.WHITE, Point(3, 3)))
        board.putStone(Stone(StoneType.WHITE, Point(4, 4)))
        board.putStone(Stone(StoneType.WHITE, Point(6, 6)))
        board.putStone(Stone(StoneType.WHITE, Point(5, 5)))

        assertThat(board.isWinCondition(Stone(StoneType.WHITE, Point(5, 5)))).isTrue
    }

    @Test
    fun `블랙 타입의 돌이 우하향 대각선으로 5개 연속되어 있을때 게임 종료`() {
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))
        board.putStone(Stone(StoneType.BLACK, Point(6, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(7, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(8, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(9, 1)))

        repeat(5) {
            assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(5 + it, 5 - it)))).isTrue
        }
    }

    @Test
    fun `블랙 타입의 돌은 우하향 대각선으로 6개 이상 연속되어 있다면 게임이 종료되지 않는다 `() {
        board.putStone(Stone(StoneType.BLACK, Point(5, 5)))
        board.putStone(Stone(StoneType.BLACK, Point(6, 4)))
        board.putStone(Stone(StoneType.BLACK, Point(7, 3)))
        board.putStone(Stone(StoneType.BLACK, Point(8, 2)))
        board.putStone(Stone(StoneType.BLACK, Point(10, 0)))
        board.putStone(Stone(StoneType.BLACK, Point(9, 1)))

        assertThat(board.isWinCondition(Stone(StoneType.BLACK, Point(9, 1)))).isFalse
    }

    @Test
    fun `화이트 타입의 돌이 우하향 대각선으로 6개 연속되어 있어도 게임 종료`() {
        board.putStone(Stone(StoneType.WHITE, Point(5, 5)))
        board.putStone(Stone(StoneType.WHITE, Point(6, 4)))
        board.putStone(Stone(StoneType.WHITE, Point(7, 3)))
        board.putStone(Stone(StoneType.WHITE, Point(8, 2)))
        board.putStone(Stone(StoneType.WHITE, Point(10, 0)))
        board.putStone(Stone(StoneType.WHITE, Point(9, 1)))

        assertThat(board.isWinCondition(Stone(StoneType.WHITE, Point(9, 1)))).isTrue
    }

    @Test
    fun `보드는 가장 최근의 착수된 돌의 정보를 가지고 있다`() {
        board.putStone(Stone(StoneType.WHITE, Point(5, 5)))
        board.putStone(Stone(StoneType.WHITE, Point(6, 4)))

        assertThat(board.latestStone).isEqualTo(Stone(StoneType.WHITE, Point(6, 4)))
    }
}
