package domain.board

import omok.domain.OmokCheck
import omok.domain.board.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokCheckTest {
    @Test
    fun `오목 테스트1`() {
        val board = OmokBoard()
        board.addStone(Point(OmokColumn.A, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.B, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.C, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.D, OmokRow.EIGHT, StoneStatus.BLACK))
        val result = OmokCheck(board).isOmok(Point(OmokColumn.E, OmokRow.EIGHT, StoneStatus.BLACK))
        assertThat(result).isTrue()
    }

    @Test
    fun `오목 테스트2`() {
        val board = OmokBoard()
        board.addStone(Point(OmokColumn.A, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.B, OmokRow.SEVEN, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.D, OmokRow.FIVE, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.E, OmokRow.FOUR, StoneStatus.BLACK))
        val result = OmokCheck(board).isOmok(Point(OmokColumn.C, OmokRow.SIX, StoneStatus.BLACK))
        assertThat(result).isTrue()
    }

    @Test
    fun `오목 테스트3`() {
        val board = OmokBoard()
        board.addStone(Point(OmokColumn.A, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.C, OmokRow.TEN, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.D, OmokRow.ELEVEN, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.E, OmokRow.TWELVE, StoneStatus.BLACK))
        val result = OmokCheck(board).isOmok(Point(OmokColumn.B, OmokRow.NINE, StoneStatus.BLACK))
        assertThat(result).isTrue()
    }

    @Test
    fun `오목 테스트4`() {
        val board = OmokBoard()
        board.addStone(Point(OmokColumn.A, OmokRow.EIGHT, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.B, OmokRow.SEVEN, StoneStatus.WHITE))
        board.addStone(Point(OmokColumn.C, OmokRow.SIX, StoneStatus.BLACK))
        board.addStone(Point(OmokColumn.D, OmokRow.FIVE, StoneStatus.BLACK))
        val result = OmokCheck(board).isOmok(Point(OmokColumn.E, OmokRow.FOUR, StoneStatus.BLACK))
        assertThat(result).isFalse()
    }
}