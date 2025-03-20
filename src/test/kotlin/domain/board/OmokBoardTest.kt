package domain.board

import domain.fixture.omokBoardFixture
import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.Direction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokBoardTest {
    private lateinit var omokBoard: OmokBoard

    @BeforeEach
    fun setUp() {
        omokBoard = omokBoardFixture()
    }

    @Test
    fun `돌을 착수하면 바둑판에 착수한 위치에 돌이 추가된다`() {
        omokBoard.addStone(Point(x = OmokColumn.O, y = OmokRow.ONE, stoneStatus = StoneStatus.WHITE))

        val result = omokBoard.getPointAt(OmokRow.ONE, OmokColumn.O)

        assertEquals(result.stoneStatus, StoneStatus.WHITE)
    }

    @Test
    fun `이미 돌이 착수된 위치면 에러를 반환한다`() {
        omokBoard.addStone(Point(x = OmokColumn.O, y = OmokRow.ONE, stoneStatus = StoneStatus.WHITE))
        val duplicatedPosition = Point(x = OmokColumn.O, y = OmokRow.ONE, stoneStatus = StoneStatus.BLACK)

        assertThrows<IllegalArgumentException>(
            message = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요.",
        ) {
            omokBoard.pointValidation(duplicatedPosition)
        }
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, stoneStatus = StoneStatus.WHITE)
        val nextPoint = omokBoard.goto(currentPoint, Direction.BOTTOM)
        assertThat(nextPoint).isEqualTo(omokBoard.getPointAt(OmokRow.NINE, OmokColumn.H))
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다2`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, stoneStatus = StoneStatus.WHITE)
        val nextPoint = omokBoard.goto(currentPoint, Direction.LEFT)
        assertThat(nextPoint).isEqualTo(omokBoard.getPointAt(OmokRow.TEN, OmokColumn.G))
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다3`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, stoneStatus = StoneStatus.WHITE)
        val nextPoint = omokBoard.goto(currentPoint, Direction.TOP_LEFT)
        assertThat(nextPoint).isEqualTo(omokBoard.getPointAt(OmokRow.ELEVEN, OmokColumn.G))
    }

    @Test
    fun `보드가 가지고 있는 좌표들을 2차원 리스트의 형태로 반환할 수 있다`() {
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, StoneStatus.BLACK))
        val formattedList = omokBoard.toMatrix()
        assertThat(formattedList[7][0]).isEqualTo(StoneStatus.BLACK)
    }

    @Test
    fun `보드가 비어 있지 않으면 참을 반환한다`() {
        assertEquals(omokBoard.isNotFull(), true)
    }
}
