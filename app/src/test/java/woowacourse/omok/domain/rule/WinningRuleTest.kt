package woowacourse.omok.domain.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class WinningRuleTest {
    @Test
    fun `검정색의 돌이 가로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1 to 5))
        // when
        val omokBoardBlackWinByHorizontal =
            OmokBoard(
                Position(1 to 1) to "Black",
                Position(1 to 2) to "Black",
                Position(1 to 3) to "Black",
                Position(1 to 4) to "Black",
            )

        /*
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        09 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        08 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        07 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        06 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        05 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        04 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        03 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        02 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        01 ●──●──●──●──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
         */

        val actual = WinningRule().place(omokBoardBlackWinByHorizontal, playerStone)
        val expected = GameFinish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5 to 1))

        // when
        val omokBoardBlackWinByVertical =
            OmokBoard(
                Position(1 to 1) to "Black",
                Position(2 to 1) to "Black",
                Position(3 to 1) to "Black",
                Position(4 to 1) to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByVertical, playerStone)
        val expected = GameFinish(GameResult.WIN_BLACK)
        /*
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        09 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        08 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        07 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        06 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        05 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        04 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        03 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        02 ●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        01 ●──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
         */

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 대각선으로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5 to 5))

        // when
        val omokBoardBlackWinByDiagonal =
            OmokBoard(
                Position(1 to 1) to "Black",
                Position(2 to 2) to "Black",
                Position(3 to 3) to "Black",
                Position(4 to 4) to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByDiagonal, playerStone)
        val expected = GameFinish(GameResult.WIN_BLACK)

        /*
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        09 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        08 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        07 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        06 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        05 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        04 ├──┼──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        03 ├──┼──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        02 ├──●──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        01 ●──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
         */

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나란히 있는 검은돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1 to 3))

        // when
        val omokBoardBlackWinByBetween =
            OmokBoard(
                Position(1 to 1) to "Black",
                Position(1 to 2) to "Black",
                Position(1 to 4) to "Black",
                Position(1 to 5) to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByBetween, playerStone)
        val expected = GameFinish(GameResult.WIN_BLACK)

        /*
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        09 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        08 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        07 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        06 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        05 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        04 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        03 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        02 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        01 ●──●──┴──●──●──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
         */

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌이 가로로 5개 있으면 흰색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1 to 5))
        // when
        val omokBoardWhiteWinByHorizontal =
            OmokBoard(
                Position(1 to 1) to "White",
                Position(1 to 2) to "White",
                Position(1 to 3) to "White",
                Position(1 to 4) to "White",
            )

        val actual = WinningRule().place(omokBoardWhiteWinByHorizontal, playerStone)
        val expected = GameFinish(GameResult.WIN_WHITE)

        /*
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        09 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        08 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        07 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        06 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        05 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        04 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        03 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        02 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        01 ○──○──○──○──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
         */

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
