package domain.board

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.Point
import omok.domain.board.StoneStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `이미 돌이 착수된 위치면 에러를 반환한다`() {
        val duplicatedPosition = Point(x = OmokColumn.O, y = OmokRow.ONE, stoneStatus = StoneStatus.WHITE)
        omokBoard.addStone(duplicatedPosition)

        assertThrows<IllegalArgumentException>(
            message = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요.",
        ) {
            omokBoard.addStone(duplicatedPosition)
        }
    }
}
