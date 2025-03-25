package domain.rule

import domain.fixture.blackAByEight
import domain.fixture.blackBByEight
import domain.fixture.blackBByEleven
import domain.fixture.blackDByEight
import domain.fixture.blackDByFive
import domain.fixture.blackDByFour
import domain.fixture.blackDByNine
import domain.fixture.blackDBySeven
import domain.fixture.blackDBySix
import domain.fixture.blackEByEight
import domain.fixture.blackEByFive
import domain.fixture.blackEByFour
import domain.fixture.blackFByEight
import domain.fixture.blackFByFive
import domain.fixture.blackFByFour
import domain.fixture.blackFBySeven
import domain.fixture.blackGByFive
import domain.fixture.blackGByNine
import domain.fixture.blackGBySeven
import domain.fixture.blackGBySix
import domain.fixture.blackGByTen
import domain.fixture.blackIByEight
import domain.fixture.emptyDByEight
import domain.fixture.emptyDByFive
import domain.fixture.emptyDByFour
import domain.fixture.emptyEByEight
import domain.fixture.emptyGByEight
import domain.fixture.emptyHByFive
import domain.fixture.omokBoardFixture
import domain.fixture.whiteBByEight
import domain.fixture.whiteBByEleven
import domain.fixture.whiteDByEight
import domain.fixture.whiteDByNine
import domain.fixture.whiteEByEleven
import domain.fixture.whiteFByEight
import domain.fixture.whiteHByEight
import omok.domain.board.OmokBoard
import omok.domain.rule.renju.ThreeByThree
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ThreeByThreeTest {
    private lateinit var board: OmokBoard

    @BeforeEach
    fun setUp() {
        board = omokBoardFixture()
    }

    /**
     //     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     //     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   8 ├──┼──┼──┼──●──●──X──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   7 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   6 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     //     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     //     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     //     * */
    @Test
    fun `3x3 검사`() {
        board.addStone(blackEByEight)
        board.addStone(blackFByEight)
        board.addStone(blackGBySeven)
        board.addStone(blackGBySix)
        val result = ThreeByThree(board).match(emptyGByEight)
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
     *   7 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `3x3 검사시 거짓을 반환한다`() {
        board.addStone(whiteDByEight)
        board.addStone(blackEByEight)
        board.addStone(blackFByEight)
        board.addStone(blackGBySeven)
        board.addStone(blackGBySix)

        val result = ThreeByThree(board).match(emptyGByEight)

        assertThat(result).isFalse()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──●──●──┼──X──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `3x3테스트4`() {
        board.addStone(blackEByEight)
        board.addStone(blackFBySeven)
        board.addStone(blackEByFive)
        board.addStone(blackFByFive)
        val result = ThreeByThree(board).match(emptyHByFive)
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
     *   8 ●──●──┼──X──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   7 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   6 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
     *     A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     * */
    @Test
    fun `3x3테스트5`() {
        board.addStone(blackAByEight)
        board.addStone(blackBByEight)
        board.addStone(blackDBySeven)
        board.addStone(blackDBySix)
        val result = ThreeByThree(board).match(emptyDByEight)
        assertThat(result).isFalse()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──┼──●──X──●──┼──┼──┼──┼──┼──┼──┼──┼──┤
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
    fun `3x3테스트6`() {
        board.addStone(blackDByEight)
        board.addStone(blackFByEight)
        board.addStone(blackDByNine)
        board.addStone(blackBByEleven)
        val result = ThreeByThree(board).match(emptyEByEight)
        assertThat(result).isTrue()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──○──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──○──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──┼──┼──○──X──○──┼──┼──┼──┼──┼──┼──┼──┼──┤
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
    fun `3x3테스트7`() {
        board.addStone(whiteDByEight)
        board.addStone(whiteFByEight)
        board.addStone(whiteDByNine)
        board.addStone(whiteEByEleven)
        val result = ThreeByThree(board).match(emptyEByEight)
        assertThat(result).isFalse()
    }

    @Test
    fun `3x3테스트8`() {
        board.addStone(blackDByFour)
        board.addStone(blackDBySeven)
        board.addStone(blackFByFive)
        board.addStone(blackGByFive)
        val result = ThreeByThree(board).match(emptyDByFive)
        assertThat(result).isTrue()
    }

    @Test
    fun `3x3테스트9`() {
        board.addStone(blackFByFour)
        board.addStone(blackEByFour)
        board.addStone(blackDByFive)
        board.addStone(blackDBySix)
        val result = ThreeByThree(board).match(emptyDByFour)
        assertThat(result).isTrue()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──○──┼──●──X──●──┼──○──┼──┼──┼──┼──┼──┼──┤
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
    fun `거짓금수 3x3테스트시 E8은 거짓 금수 자리에 해당하지 않는다 `() {
        board.addStone(whiteBByEight)
        board.addStone(whiteHByEight)
        board.addStone(blackDByEight)
        board.addStone(blackFByEight)
        board.addStone(blackDByNine)
        board.addStone(whiteBByEleven)

        val result = ThreeByThree(board).match(emptyEByEight)
        assertThat(result).isFalse()
    }

    /**
     *  15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
     *  14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
     *  10 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   9 ├──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┤
     *   8 ├──●──┼──┼──●──●──┼──┼──○──┼──┼──┼──┼──┼──┤
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
    fun `거짓금수 3x3 판별시 G8은 거짓금수 자리에 해당하지 않는다`() {
        // given
        board.addStone(blackBByEight)
        board.addStone(blackEByEight)
        board.addStone(blackFByEight)
        board.addStone(blackGByNine)
        board.addStone(blackGByTen)
        board.addStone(blackIByEight)

        // when
        val result = ThreeByThree(board).match(emptyGByEight)

        // reuslt
        assertThat(result).isFalse()
    }
}
