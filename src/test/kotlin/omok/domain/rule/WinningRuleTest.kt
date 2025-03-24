package omok.domain.rule

import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WinningRuleTest {
    private lateinit var playingBoard: PlayingBoard

    @BeforeEach
    fun setup() {
        playingBoard = PlayingBoard(rules = OmokRule.rules)
    }

    @Test
    fun `검정색의 돌이 가로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'A'))
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'B'))
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'C'))
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'D'))
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'E'))

        // when
        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)
        playingBoard.placeStone(playerStone3)
        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().place(playingBoard.board, playerStone5)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'A'))
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, Position(2, 'A'))
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, Position(3, 'A'))
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, Position(4, 'A'))
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5, 'A'))

        // when
        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)
        playingBoard.placeStone(playerStone3)
        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().place(playingBoard.board, playerStone5)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 대각선으로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'A'))
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, Position(2, 'B'))
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, Position(3, 'C'))
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, Position(4, 'D'))
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, Position(5, 'E'))

        // when
        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)
        playingBoard.placeStone(playerStone3)
        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().place(playingBoard.board, playerStone5)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나란히 있는 검은돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'A'))
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'B'))
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'C'))
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'D'))
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, Position(1, 'E'))

        // when
        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)

        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().place(playingBoard.board, playerStone3)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌이 가로로 5개 있으면 흰색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'A'))
        val playerStone2: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'B'))
        val playerStone3: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'C'))
        val playerStone4: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'D'))
        val playerStone5: PlayerStone = PlayerStone(StoneColor.WHITE, Position(1, 'E'))

        // when
        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)
        playingBoard.placeStone(playerStone3)
        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().place(playingBoard.board, playerStone5)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_WHITE)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
