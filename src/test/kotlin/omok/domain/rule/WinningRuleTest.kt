package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.Position
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WinningRuleTest {
    @Test
    fun `검정색의 돌이 가로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'E'))
        // when
        val omokBoardBlackWinByHorizontal =
            OmokBoard(
                "A1" to "Black",
                "B1" to "Black",
                "C1" to "Black",
                "D1" to "Black",
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
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5, 'A'))

        // when
        val omokBoardBlackWinByVertical =
            OmokBoard(
                "A1" to "Black",
                "A2" to "Black",
                "A3" to "Black",
                "A4" to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByVertical, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)
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
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5, 'E'))

        // when
        val omokBoardBlackWinByDiagonal =
            OmokBoard(
                "A1" to "Black",
                "B2" to "Black",
                "C3" to "Black",
                "D4" to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByDiagonal, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

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
        val playerStone: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'C'))

        // when
        val omokBoardBlackWinByBetween =
            OmokBoard(
                "A1" to "Black",
                "B1" to "Black",
                "D1" to "Black",
                "E1" to "Black",
            )

        val actual = WinningRule().place(omokBoardBlackWinByBetween, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

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
        val playerStone: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'E'))
        // when
        val omokBoardWhiteWinByHorizontal =
            OmokBoard(
                "A1" to "White",
                "B1" to "White",
                "C1" to "White",
                "D1" to "White",
            )

        val actual = WinningRule().place(omokBoardWhiteWinByHorizontal, playerStone)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_WHITE)

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
