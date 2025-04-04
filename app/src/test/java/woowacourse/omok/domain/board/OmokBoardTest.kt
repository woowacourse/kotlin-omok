package woowacourse.omok.domain.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.exception.OmokException
import woowacourse.omok.domain.rule.Direction
import woowacourse.omok.fixture.blackAByEight
import woowacourse.omok.fixture.blackBByEight
import woowacourse.omok.fixture.blackBByNine
import woowacourse.omok.fixture.blackBBySeven
import woowacourse.omok.fixture.blackCByEight
import woowacourse.omok.fixture.blackCBySix
import woowacourse.omok.fixture.blackCByTen
import woowacourse.omok.fixture.blackDByEight
import woowacourse.omok.fixture.blackDByEleven
import woowacourse.omok.fixture.blackDByFive
import woowacourse.omok.fixture.blackEByEight
import woowacourse.omok.fixture.blackEByFour
import woowacourse.omok.fixture.blackEByTwelve
import woowacourse.omok.fixture.blackFByEight
import woowacourse.omok.fixture.blackFifteenByOne
import woowacourse.omok.fixture.blackGBySeven
import woowacourse.omok.fixture.blackGBySix
import woowacourse.omok.fixture.emptyGByEleven
import woowacourse.omok.fixture.emptyGByTen
import woowacourse.omok.fixture.emptyHByNine
import woowacourse.omok.fixture.omokBoardFixture
import woowacourse.omok.fixture.whiteBByEleven
import woowacourse.omok.fixture.whiteGByEight
import woowacourse.omok.fixture.whiteHByTen
import woowacourse.omok.fixture.whiteOByOne

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
        val duplicatedPosition = blackFifteenByOne

        // result
        assertThrows<OmokException.OccupiedExceptions> {
            omokBoard.pointValidation(duplicatedPosition)
        }
    }

    @Test
    fun `전진 방향과 현재 좌표를 입력하면 전진하려는 방향의 다음 좌표를 반환한다`() {
        // given
        val currentPoint = whiteHByTen

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.BOTTOM)

        // result
        assertThat(nextPoint).isEqualTo(emptyHByNine)
    }

    @Test
    fun `현재 좌표에서 왼쪽 방향으로 한 칸 이동한 후, 해당 좌표를 반환한다`() {
        // given
        val currentPoint = whiteHByTen

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.LEFT)

        // result
        assertThat(nextPoint).isEqualTo(emptyGByTen)
    }

    @Test
    fun `현재 좌표에서 좌상단으로 전진하면 해당 위치의 좌표를 반환한다`() {
        // given
        val currentPoint = whiteHByTen

        // when
        val nextPoint = omokBoard.goto(currentPoint, Direction.TOP_LEFT)

        // result
        assertThat(nextPoint).isEqualTo(emptyGByEleven)
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
        val result = omokBoard.isOmok(blackEByEight)

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
        omokBoard.addStone(blackAByEight)
        omokBoard.addStone(blackCByTen)
        omokBoard.addStone(blackDByEleven)
        omokBoard.addStone(blackEByTwelve)

        // when
        val result = omokBoard.isOmok(blackBByNine)

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
        omokBoard.addStone(blackAByEight)
        omokBoard.addStone(blackBBySeven)
        omokBoard.addStone(blackDByFive)
        omokBoard.addStone(blackEByFour)

        // when
        val result = omokBoard.isOmok(blackCBySix)

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
        omokBoard.addStone(blackAByEight)
        omokBoard.addStone(whiteBByEleven)
        omokBoard.addStone(blackCBySix)
        omokBoard.addStone(blackDByFive)

        // when
        val result = omokBoard.isOmok(blackEByFour)

        // result
        assertThat(result).isFalse()
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
