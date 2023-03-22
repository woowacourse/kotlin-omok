package domain.game

import domain.player.Players
import domain.stone.StoneColor
import listener.OmokGameEventListener
import listener.OmokTurnEventListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokTest {
    private lateinit var rule: OmokRule

    private val blackPositions: MutableList<Point> by lazy {
        mutableListOf(
            Point(1, 1),
            Point(1, 2),
            Point(1, 3),
            Point(1, 4),
            Point(1, 5),
        )
    }
    private val whitePositions: MutableList<Point> by lazy {
        mutableListOf(
            Point(3, 3),
            Point(3, 4),
            Point(3, 10),
            Point(5, 5),
            Point(6, 5),
        )
    }

    @BeforeEach
    fun setUp() {
        rule = RenjuRule()
    }

    @Test
    fun `한 플레이어라도 승리할 때까지 차례를 번갈아가면서 돌을 놓는다`() {
        var round = 0
        val gameListener = object : OmokGameEventListener {
            override fun onStartTurn(stoneColor: StoneColor, position: Point) {
                assertThat(stoneColor).isEqualTo(StoneColor.BLACK)
            }

            override fun onEndTurn(players: Players) { }

            override fun onStartGame() {}

            override fun onEndGame(winnerStoneColor: StoneColor) {
                assertThat(winnerStoneColor).isEqualTo(StoneColor.BLACK)
            }
        }
        val turnListener = object : OmokTurnEventListener {
            override fun onTakeTurn(stoneColor: StoneColor): Point {
                return if (round % 2 == 0) {
                    round++
                    blackPositions.removeFirst()
                } else {
                    round++
                    whitePositions.removeFirst()
                }
            }

            override fun onStoneNotPlaceable() {}
        }
        val omok = Omok(turnListener, gameListener, rule)
        omok.run()
    }
}
