package omok.model.game

import omok.model.Board
import omok.model.adapter.BlackRenjuRuleAdapter
import omok.model.adapter.WhiteRenjuRuleAdapter
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor.BLACK
import omok.model.stone.StoneColor.WHITE
import omok.model.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `플레이어가 수를 두면 마지막 돌이 바뀐다`() {
        val game = Game(Board())
        game.play(Stone(Point(8, 8), WHITE))

        val actual: Stone? = game.lastStone

        val expected = Stone(Point(8, 8), WHITE)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드가 흑 오목이면 흑 오목 상태를 받을 수 있다`() {
        val game =
            Game(
                Board(
                    blackStones =
                        Stones(
                            setOf(
                                Stone(8, 8, BLACK),
                                Stone(8, 9, BLACK),
                                Stone(8, 10, BLACK),
                                Stone(8, 11, BLACK),
                            ),
                            BlackRenjuRuleAdapter(),
                        ),
                ),
            )
        val newStone = Stone(8, 12, BLACK)

        val actual = game.gameState(newStone)

        val expected = GameState.BLACK_OMOK
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드가 백 오목이면 백 오목 상태를 받을 수 있다`() {
        val game =
            Game(
                Board(
                    whiteStones =
                        Stones(
                            setOf(
                                Stone(8, 8, WHITE),
                                Stone(8, 9, WHITE),
                                Stone(8, 10, WHITE),
                                Stone(8, 11, WHITE),
                            ),
                            WhiteRenjuRuleAdapter(),
                        ),
                ),
            )
        val newStone = Stone(8, 12, WHITE)

        val actual = game.gameState(newStone)

        val expected = GameState.WHITE_OMOK
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에 오목이 없으면 게임중 상태를 받을 수 있다`() {
        val game =
            Game(
                Board(
                    blackStones =
                        Stones(
                            setOf(
                                Stone(8, 8, BLACK),
                                Stone(8, 10, BLACK),
                            ),
                            BlackRenjuRuleAdapter(),
                        ),
                ),
            )
        val newStone = Stone(8, 12, BLACK)

        val actual = game.gameState(newStone)

        val expected = GameState.PLAYING
        assertThat(actual).isEqualTo(expected)
    }
}
