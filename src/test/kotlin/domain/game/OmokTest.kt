package domain.game

import domain.player.Players
import domain.position.Position
import domain.rule.OmokRule
import domain.rule.RenjuRule
import domain.stone.StoneColor
import listener.OmokGameEventListener
import listener.OmokTurnEventListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokTest {
    private lateinit var rule: OmokRule

    private val blackPositions: MutableList<Position> by lazy {
        mutableListOf(
            Position(1, 1),
            Position(1, 2),
            Position(1, 3),
            Position(1, 4),
            Position(1, 5),
        )
    }
    private val whitePositions: MutableList<Position> by lazy {
        mutableListOf(
            Position(3, 3),
            Position(3, 4),
            Position(3, 10),
            Position(5, 5),
            Position(6, 5),
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
            override fun onStartTurn(stoneColor: StoneColor, position: Position) {
                assertThat(stoneColor).isEqualTo(StoneColor.BLACK)
            }

            override fun onEndTurn(players: Players) { }

            override fun onStartGame() {}

            override fun onEndGame(winnerStoneColor: StoneColor) {
                assertThat(winnerStoneColor).isEqualTo(StoneColor.BLACK)
            }
        }
        val turnListener = object : OmokTurnEventListener {
            override fun onTakeTurn(stoneColor: StoneColor): Position {
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
