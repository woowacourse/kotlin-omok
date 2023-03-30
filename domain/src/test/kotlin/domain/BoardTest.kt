package domain

import domain.rule.RenjuRule
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BoardTest {

    @Test
    fun `특정 팀의 마지막으로 넣은 돌의 좌표를 알 수 있다`() {
        val point = Point('A', 1)
        val board = Board(RenjuRule).apply {
            place(Team.WHITE, Stone(point))
        }

        assertThat(board.getLastPoint(Team.WHITE)).isEqualTo(point)
    }

    @Test
    fun `오목판의 범위를 벗어난 돌을 놓으려 하면 에러가 발생한다`() {
        val stone = Stone('Z', 1)
        val board = Board(RenjuRule)

        assertThatIllegalArgumentException().isThrownBy { board.place(Team.WHITE, stone) }
            .withMessage("돌이 오목판의 범위를 벗어났습니다.\n돌의 좌표: ${stone.point}")
    }

    @Test
    fun `이미 돌이 놓여진 위치에 돌을 놓으려 하면 에러가 발생한다`() {
        val stone = Stone('A', 1)
        val board = Board(RenjuRule).apply { place(Team.BLACK, stone) }

        assertThatIllegalArgumentException().isThrownBy { board.place(Team.WHITE, stone) }
            .withMessage("이미 놓여진 위치에 돌을 둘 수 없습니다.\n돌의 좌표: ${stone.point}")
    }

    @Test
    fun `돌을 뒀을 때 규칙에 어긋나면 에러가 발생한다`() {
        val board = Board(RenjuRule).apply {
            place(Team.BLACK, Stone('C', 12))
            place(Team.BLACK, Stone('E', 12))
            place(Team.BLACK, Stone('D', 13))
            place(Team.BLACK, Stone('D', 14))
        }

        val stone = Stone('D', 12)
        assertThatIllegalStateException().isThrownBy { board.place(Team.BLACK, stone) }
            .withMessage("규칙을 어겼습니다.")
    }

    @Test
    fun `규칙을 어기지 않는다면 오목판의 돌이 없는 좌표에 돌을 놓을 수 있다`() {
        val board = Board(RenjuRule).apply {
            place(Team.BLACK, Stone('C', 12))
            place(Team.BLACK, Stone('E', 12))
            place(Team.BLACK, Stone('D', 13))
            place(Team.BLACK, Stone('D', 14))
        }

        assertDoesNotThrow { board.place(Team.WHITE, Stone('D', 12)) }
    }

    @Test
    fun `특정 팀의 돌이 오목판 안에 있는지 알 수 있다`() {
        val board = Board(RenjuRule).apply {
            place(Team.BLACK, Stone('C', 12))
            place(Team.BLACK, Stone('E', 12))
            place(Team.BLACK, Stone('D', 13))
            place(Team.BLACK, Stone('D', 14))
        }

        assertThat(board.isPlaced(Team.BLACK, Stone('C', 12))).isTrue
    }
}