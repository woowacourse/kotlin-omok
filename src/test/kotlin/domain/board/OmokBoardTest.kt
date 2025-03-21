package domain.board

import domain.fixture.blackAByEight
import domain.fixture.blackBByEight
import domain.fixture.blackCByEight
import domain.fixture.blackDByEight
import domain.fixture.blackStone
import domain.fixture.omokBoardFixture
import domain.fixture.whiteOByOne
import domain.fixture.whiteStone
import omok.domain.board.BoardStatus
import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
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
    fun `이미 돌이 착수된 위치면 에러를 반환한다`() {
        omokBoard.addStone(whiteOByOne)
        val duplicatedPosition = Point(x = OmokColumn.O, y = OmokRow.ONE, status = blackStone)

        assertThrows<IllegalArgumentException>(
            message = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요.",
        ) {
            omokBoard.pointValidation(duplicatedPosition)
        }
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)
        val nextPoint = omokBoard.goto(currentPoint, Direction.BOTTOM)
        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.H, y = OmokRow.NINE, status = BoardStatus.Empty))
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다2`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)
        val nextPoint = omokBoard.goto(currentPoint, Direction.LEFT)
        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.G, y = OmokRow.TEN, status = BoardStatus.Empty))
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다3`() {
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)
        val nextPoint = omokBoard.goto(currentPoint, Direction.TOP_LEFT)

        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.G, y = OmokRow.ELEVEN, status = BoardStatus.Empty))
    }

    @Test
    fun `보드가 가지고 있는 좌표들을 2차원 리스트의 형태로 반환할 수 있다`() {
        omokBoard.addStone(blackAByEight)
        val formattedList = omokBoard.toMatrix()
        assertThat(formattedList[7][0]).isEqualTo(blackStone)
    }

    @Test
    fun `보드가 비어 있지 않으면 참을 반환한다`() {
        assertEquals(omokBoard.isNotFull(), true)
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ●──●──●──●──X──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `오목 테스트1`() {
        omokBoard.addStone(blackAByEight)
        omokBoard.addStone(blackBByEight)
        omokBoard.addStone(blackCByEight)
        omokBoard.addStone(blackDByEight)
        val result = omokBoard.isOmok(Point(OmokColumn.E, OmokRow.EIGHT, blackStone))
        assertThat(result).isTrue()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──X──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `오목 테스트2`() {
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.B, OmokRow.SEVEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.FIVE, blackStone))
        omokBoard.addStone(Point(OmokColumn.E, OmokRow.FOUR, blackStone))
        val result = omokBoard.isOmok(Point(OmokColumn.C, OmokRow.SIX, blackStone))
        assertThat(result).isTrue()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──X──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `오목 테스트3`() {
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.C, OmokRow.TEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.ELEVEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.E, OmokRow.TWELVE, blackStone))
        val result = omokBoard.isOmok(Point(OmokColumn.B, OmokRow.NINE, blackStone))
        assertThat(result).isTrue()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──X──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `오목 테스트4`() {
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.B, OmokRow.SEVEN, whiteStone))
        omokBoard.addStone(Point(OmokColumn.C, OmokRow.SIX, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.FIVE, blackStone))
        val result = omokBoard.isOmok(Point(OmokColumn.E, OmokRow.FOUR, blackStone))
        assertThat(result).isFalse()
    }
}
