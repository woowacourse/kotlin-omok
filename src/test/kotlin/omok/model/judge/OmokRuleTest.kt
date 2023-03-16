package omok.model.judge

import omok.model.Board
import omok.model.state.Stay
import omok.model.state.Win
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {
    @Test
    fun `5개의 같은 색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("H8"))
            addStone(GoStoneColor.BLACK, Coordinate("H9"))
            addStone(GoStoneColor.BLACK, Coordinate("H11"))
            addStone(GoStoneColor.BLACK, Coordinate("H12"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 세로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("F10"))
            addStone(GoStoneColor.BLACK, Coordinate("G10"))
            addStone(GoStoneColor.BLACK, Coordinate("I10"))
            addStone(GoStoneColor.BLACK, Coordinate("J10"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 우상향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("F8"))
            addStone(GoStoneColor.BLACK, Coordinate("G9"))
            addStone(GoStoneColor.BLACK, Coordinate("I11"))
            addStone(GoStoneColor.BLACK, Coordinate("J12"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `5개의 같은 색의 돌이 우하향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("F12"))
            addStone(GoStoneColor.BLACK, Coordinate("G11"))
            addStone(GoStoneColor.BLACK, Coordinate("I9"))
            addStone(GoStoneColor.BLACK, Coordinate("J8"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Win::class.java)
    }

    @Test
    fun `연달아 놓여져 있는 5개의 돌들의 색이 서로 다르면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("H8"))
            addStone(GoStoneColor.BLACK, Coordinate("H9"))
            addStone(GoStoneColor.WHITE, Coordinate("H11"))
            addStone(GoStoneColor.BLACK, Coordinate("H12"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Stay::class.java)
    }

    @Test
    fun `놓여져 있는 돌들이 5개보다 적으면 승리가 아니다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("H8"))
            addStone(GoStoneColor.BLACK, Coordinate("H9"))
            addStone(GoStoneColor.WHITE, Coordinate("H11"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Stay::class.java)
    }

    @Test
    fun `6개의 같은 색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(GoStoneColor.BLACK, Coordinate("H8"))
            addStone(GoStoneColor.BLACK, Coordinate("H9"))
            addStone(GoStoneColor.BLACK, Coordinate("H11"))
            addStone(GoStoneColor.BLACK, Coordinate("H12"))
            addStone(GoStoneColor.BLACK, Coordinate("H13"))
        }
        val goStone = GoStone(GoStoneColor.BLACK, Coordinate("H10"))
        val rule = OmokRule(board, goStone)

        assertThat(
            rule.checkWin()
        ).isInstanceOf(Win::class.java)
    }
}
