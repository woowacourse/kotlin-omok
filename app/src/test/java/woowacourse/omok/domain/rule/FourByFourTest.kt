package woowacourse.omok.domain.rule

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.rule.renju.FourByFour
import woowacourse.omok.fixture.blackCByEight
import woowacourse.omok.fixture.blackDByEight
import woowacourse.omok.fixture.blackEByEight
import woowacourse.omok.fixture.blackFByEight
import woowacourse.omok.fixture.blackGByFive
import woowacourse.omok.fixture.blackGByFour
import woowacourse.omok.fixture.blackGBySeven
import woowacourse.omok.fixture.blackGBySix
import woowacourse.omok.fixture.emptyGByEight
import woowacourse.omok.fixture.omokBoardFixture
import woowacourse.omok.fixture.whiteCByEight
import woowacourse.omok.fixture.whiteEByEight
import woowacourse.omok.fixture.whiteFByEight
import woowacourse.omok.fixture.whiteGByFour
import woowacourse.omok.fixture.whiteGBySeven
import woowacourse.omok.fixture.whiteGBySix

class FourByFourTest {
    private lateinit var board: OmokBoard

    @BeforeEach
    fun setUp() {
        board = omokBoardFixture()
    }

    @Test
    fun `4x4 금수 자리에 착수하면 예외를 반환한다`() {
        // given
        board.addStone(blackDByEight)
        board.addStone(blackEByEight)
        board.addStone(blackFByEight)
        board.addStone(blackGBySeven)
        board.addStone(blackGBySix)
        board.addStone(blackGByFive)

        // result
        assertThrows<RendjuException.DoubleFourException> {
            // when
            FourByFour(board).match(emptyGByEight)
        }
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──●──●──●──┼──X──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `4x4 매칭 테스트 - C, D, E 열과 G 열의 6, 5, 4 행에 흑돌을 배치하면 44룰에 위배된다`() {
        // given
        board.addStone(blackCByEight)
        board.addStone(blackDByEight)
        board.addStone(blackEByEight)
        board.addStone(blackGBySix)
        board.addStone(blackGByFive)
        board.addStone(blackGByFour)

        // result
        assertThrows<RendjuException.DoubleFourException> {
            // when
            FourByFour(board).match(emptyGByEight)
        }
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──●──┼──●──●──X──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `4x4 매칭 테스트 - C, E, F 열과 G 열의 7, 6, 4 행에 흑돌 배치시 44룰에 위배된다`() {
        // given
        board.addStone(blackCByEight)
        board.addStone(blackEByEight)
        board.addStone(blackFByEight)
        board.addStone(blackGBySeven)
        board.addStone(blackGBySix)
        board.addStone(blackGByFour)

        // result
        assertThrows<RendjuException.DoubleFourException> {
            // when
            FourByFour(board).match(emptyGByEight)
        }
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──○──┼──○──○──X──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──○──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `4x4 매칭 테스트 - C, D, E 열과 G 열의 7, 6, 5 행에 흑돌 배치시 백돌은 44룰을 위반하지 않는다`() {
        // given
        board.addStone(whiteCByEight)
        board.addStone(whiteEByEight)
        board.addStone(whiteFByEight)
        board.addStone(whiteGBySeven)
        board.addStone(whiteGBySix)
        board.addStone(whiteGByFour)

        // result
        assertDoesNotThrow {
            // when
            FourByFour(board).match(emptyGByEight)
        }
    }
}
