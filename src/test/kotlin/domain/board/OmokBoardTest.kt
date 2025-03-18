package domain.board

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.Point
import omok.domain.board.StoneStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokBoardTest {
    private lateinit var omokBoard: OmokBoard

    @BeforeEach
    fun setUp() {
        omokBoard = OmokBoard()
    }

    @Test
    fun `돌을 착수하면 바둑판에 착수한 위치에 돌이 추가된다`() {
        omokBoard.addStone(Point(x = OmokColumn.O, y = OmokRow.ONE, stoneStatus = StoneStatus.WHITE))

        val result = omokBoard.getPointAt(OmokRow.ONE, OmokColumn.O)

        assertEquals(result.stoneStatus, StoneStatus.WHITE)
    }
}
