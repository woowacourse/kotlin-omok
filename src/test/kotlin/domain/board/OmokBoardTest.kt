package domain.board

import domain.fixture.blackAByEight
import domain.fixture.blackBByEight
import domain.fixture.blackCByEight
import domain.fixture.blackDByEight
import domain.fixture.blackEByEight
import domain.fixture.blackFByEight
import domain.fixture.blackGByEight
import domain.fixture.blackGBySeven
import domain.fixture.blackGBySix
import domain.fixture.blackStone
import domain.fixture.omokBoardFixture
import domain.fixture.whiteGByEight
import domain.fixture.whiteOByOne
import domain.fixture.whiteStone
import omok.domain.board.BoardStatus
import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.point.Point
import omok.domain.rule.Direction
import omok.exception.ResultState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokBoardTest {
    private lateinit var omokBoard: OmokBoard

    @BeforeEach
    fun setUp() {
        omokBoard = omokBoardFixture()
    }

    @Test
    fun `이미 돌이 착수된 위치면 에러를 반환한다`() {
        // given
        omokBoard.addStone(whiteOByOne)

        // when
        val duplicatedPosition = Point(x = OmokColumn.O, y = OmokRow.ONE, status = blackStone)
        val result = omokBoard.addStone(duplicatedPosition)

        // result
        assertEquals(result, ResultState.Error("해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."))
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다`() {
        // given
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.BOTTOM)

        // result
        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.H, y = OmokRow.NINE, status = BoardStatus.Empty))
    }

    @Test
    fun `현재 좌표에서 왼쪽 방향으로 한 칸 이동한 후, 해당 좌표를 반환한다`() {
        // given
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.LEFT)

        // result
        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.G, y = OmokRow.TEN, status = BoardStatus.Empty))
    }

    @Test
    fun `현재 좌표에서 좌상단으로 전진하면 해당 위치의 좌표를 반환한다`() {
        // given
        val currentPoint = Point(x = OmokColumn.H, y = OmokRow.TEN, status = whiteStone)

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.TOP_LEFT)

        // reuslt
        assertThat(nextPoint).isEqualTo(Point(x = OmokColumn.G, y = OmokRow.ELEVEN, status = BoardStatus.Empty))
    }

    @Test
    fun `보드가 가지고 있는 좌표들을 2차원 리스트의 형태로 반환할 수 있다`() {
        // given
        omokBoard.addStone(blackAByEight)

        // when
        val formattedList = omokBoard.toMatrix()

        // result
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
    fun `오목 일치 판별 테스트`() {
        // given
        omokBoard.addStone(blackAByEight)
        omokBoard.addStone(blackBByEight)
        omokBoard.addStone(blackCByEight)
        omokBoard.addStone(blackDByEight)

        // when
        val result = omokBoard.isOmok(Point(OmokColumn.E, OmokRow.EIGHT, blackStone))

        // result
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
    fun `오목을 판별할 때 같은 돌이 5개면 참을 반환한다`() {
        // given
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.C, OmokRow.TEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.ELEVEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.E, OmokRow.TWELVE, blackStone))

        // when
        val result = omokBoard.isOmok(Point(OmokColumn.B, OmokRow.NINE, blackStone))

        // result
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
    fun `가로 방향으로 오목 일치 여부 테스트`() {
        // given
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.B, OmokRow.SEVEN, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.FIVE, blackStone))
        omokBoard.addStone(Point(OmokColumn.E, OmokRow.FOUR, blackStone))

        // when
        val result = omokBoard.isOmok(Point(OmokColumn.C, OmokRow.SIX, blackStone))

        // result
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
    fun `백돌 좌상단 방향으로 오목을 판별할 때 흑돌이 있으면 거짓을 반환한다`() {
        // given
        omokBoard.addStone(Point(OmokColumn.A, OmokRow.EIGHT, blackStone))
        omokBoard.addStone(Point(OmokColumn.B, OmokRow.SEVEN, whiteStone))
        omokBoard.addStone(Point(OmokColumn.C, OmokRow.SIX, blackStone))
        omokBoard.addStone(Point(OmokColumn.D, OmokRow.FIVE, blackStone))

        // when
        val result = omokBoard.isOmok(Point(OmokColumn.E, OmokRow.FOUR, blackStone))

        // reuslt
        assertThat(result).isFalse()
    }

    @Test
    fun `검은돌은 금수 자리에 바둑돌을 놓으면 에러가 발생한다`() {
        // given
        omokBoard.addStone(blackEByEight)
        omokBoard.addStone(blackFByEight)
        omokBoard.addStone(blackGBySeven)
        omokBoard.addStone(blackGBySix)

        // when
        val result = omokBoard.addStone(blackGByEight)

        // result
        assertEquals(result, ResultState.Error("해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."))
    }

    @Test
    fun `흰돌은 금수 자리에 착수해도 에러가 발생하지 않는다`() {
        // given
        omokBoard.addStone(blackEByEight)
        omokBoard.addStone(blackFByEight)
        omokBoard.addStone(blackGBySeven)
        omokBoard.addStone(blackGBySix)

        // result
        assertDoesNotThrow { omokBoard.addStone(whiteGByEight) }
    }
}
