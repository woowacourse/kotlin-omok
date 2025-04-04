package woowacourse.omok.domain.rule

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.rule.renju.SixMok
import woowacourse.omok.fixture.blackBByEight
import woowacourse.omok.fixture.blackCByEight
import woowacourse.omok.fixture.blackDByEight
import woowacourse.omok.fixture.blackEByEight
import woowacourse.omok.fixture.blackGByEight
import woowacourse.omok.fixture.emptyFByEight
import woowacourse.omok.fixture.omokBoardFixture
import woowacourse.omok.fixture.whiteBByEight
import woowacourse.omok.fixture.whiteCByEight
import woowacourse.omok.fixture.whiteDByEight
import woowacourse.omok.fixture.whiteEByEight
import woowacourse.omok.fixture.whiteGByEight

class SixMokTest {
    private lateinit var board: OmokBoard

    @BeforeEach
    fun setUp() {
        board = omokBoardFixture()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──●──●──●──●──X──●──┼──┼──┼──┼──┼──┼──┼──┤
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
    fun `흑이 6목 이상의 장목이면 참을 반환한다`() {
        // given
        board.addStone(blackBByEight)
        board.addStone(blackCByEight)
        board.addStone(blackDByEight)
        board.addStone(blackEByEight)
        board.addStone(blackGByEight)

        // result
        assertThrows<RendjuException.OverLineException> {
            // when
            SixMok(board).match(emptyFByEight)
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
     *   8 ├──○──○──○──○──X──○──┼──┼──┼──┼──┼──┼──┼──┤
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
    fun `백이 6이상의 장목이면 예외가 발생하지 않는다`() {
        // given
        board.addStone(whiteBByEight)
        board.addStone(whiteCByEight)
        board.addStone(whiteDByEight)
        board.addStone(whiteEByEight)
        board.addStone(whiteGByEight)

        // result
        assertDoesNotThrow {
            // when
            SixMok(board).match(emptyFByEight)
        }
    }
}
