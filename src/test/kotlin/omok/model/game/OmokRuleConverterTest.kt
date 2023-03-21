package omok.model.game

import OmokRuleConverter
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor.BLACK
import omok.model.stone.GoStoneColor.WHITE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleConverterTest {
    @Test
    fun `5개의 같은 색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("H8"))
            addStone(BLACK, Coordinate.of("H9"))
            addStone(BLACK, Coordinate.of("H11"))
            addStone(BLACK, Coordinate.of("H12"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkBlackWin(Coordinate.of("H10"))
        ).isEqualTo(State.Win)
    }

    @Test
    fun `5개의 같은 색의 돌이 세로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("F10"))
            addStone(BLACK, Coordinate.of("G10"))
            addStone(BLACK, Coordinate.of("I10"))
            addStone(BLACK, Coordinate.of("J10"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkBlackWin(Coordinate.of("H10"))
        ).isEqualTo(State.Win)
    }

    @Test
    fun `5개의 같은 색의 돌이 우상향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("F8"))
            addStone(BLACK, Coordinate.of("G9"))
            addStone(BLACK, Coordinate.of("I11"))
            addStone(BLACK, Coordinate.of("J12"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkWhiteWin(Coordinate.of("H10"))
        ).isEqualTo(State.Win)
    }

    @Test
    fun `5개의 같은 색의 돌이 우하향 대각선으로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("F12"))
            addStone(BLACK, Coordinate.of("G11"))
            addStone(BLACK, Coordinate.of("I9"))
            addStone(BLACK, Coordinate.of("J8"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkBlackWin(Coordinate.of("H10"))
        ).isEqualTo(State.Win)
    }

    @Test
    fun `연달아 놓여져 있는 5개의 돌들의 색이 서로 다르면 승리가 아니다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("H8"))
            addStone(BLACK, Coordinate.of("H9"))
            addStone(WHITE, Coordinate.of("H11"))
            addStone(BLACK, Coordinate.of("H12"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkBlackWin(Coordinate.of("H10"))
        ).isEqualTo(State.Stay)
    }

    @Test
    fun `놓여져 있는 돌들이 5개보다 적으면 승리가 아니다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("H8"))
            addStone(BLACK, Coordinate.of("H9"))
            addStone(BLACK, Coordinate.of("H11"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.checkBlackWin(Coordinate.of("H10"))
        ).isEqualTo(State.Stay)
    }

    @Test
    fun `6개의 흰색의 돌이 가로로 연이어 놓이면 승리이다`() {
        val board = Board().apply {
            addStone(WHITE, Coordinate.of("H8"))
            addStone(WHITE, Coordinate.of("H9"))
            addStone(WHITE, Coordinate.of("H11"))
            addStone(WHITE, Coordinate.of("H12"))
            addStone(WHITE, Coordinate.of("H13"))
        }
        val rule = OmokRuleConverter(board, WHITE)

        assertThat(
            rule.checkWhiteWin(Coordinate.of("H10"))
        ).isEqualTo(State.Win)
    }

    @Test
    fun `흑돌이 열린 33이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("C12"))
            addStone(BLACK, Coordinate.of("E12"))
            addStone(BLACK, Coordinate.of("D13"))
            addStone(BLACK, Coordinate.of("D14"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenThrees(Coordinate.of("D12"))
        )
    }

    @Test
    fun `흑돌이 열린 33이다 2`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("B6"))
            addStone(BLACK, Coordinate.of("C5"))
            addStone(BLACK, Coordinate.of("E6"))
            addStone(BLACK, Coordinate.of("E5"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenThrees(Coordinate.of("E3"))
        ).isEqualTo(State.DoubleThree)
    }

    @Test
    fun `흑돌이 열린 33이다 3`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("J9"))
            addStone(BLACK, Coordinate.of("N9"))
            addStone(BLACK, Coordinate.of("M10"))
            addStone(BLACK, Coordinate.of("M12"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenThrees(Coordinate.of("L11"))
        ).isEqualTo(State.DoubleThree)
    }

    @Test
    fun `흑돌이 열린 33이다 4`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("K6"))
            addStone(BLACK, Coordinate.of("K3"))
            addStone(BLACK, Coordinate.of("M4"))
            addStone(BLACK, Coordinate.of("N4"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenThrees(Coordinate.of("K4"))
        ).isEqualTo(State.DoubleThree)
    }

    @Test
    fun `흑돌이 44이다`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("E5"))
            addStone(BLACK, Coordinate.of("F5"))
            addStone(BLACK, Coordinate.of("G5"))
            addStone(BLACK, Coordinate.of("H6"))
            addStone(BLACK, Coordinate.of("H7"))
            addStone(BLACK, Coordinate.of("H8"))
            addStone(WHITE, Coordinate.of("D5"))
            addStone(WHITE, Coordinate.of("H9"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenFours(Coordinate.of("H5"))
        ).isEqualTo(State.DoubleFour)
    }

    @Test
    fun `흑돌이 44이다 2`() {
        val board = Board().apply {
            addStone(BLACK, Coordinate.of("C12"))
            addStone(BLACK, Coordinate.of("D12"))
            addStone(BLACK, Coordinate.of("G12"))
            addStone(BLACK, Coordinate.of("I12"))
            addStone(BLACK, Coordinate.of("J12"))
        }
        val rule = OmokRuleConverter(board, BLACK)

        assertThat(
            rule.countOpenFours(Coordinate.of("F12"))
        ).isEqualTo(State.DoubleFour)
    }
}
