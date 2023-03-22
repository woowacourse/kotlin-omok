package game

import domain.game.Omok
import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import domain.state.FoulState
import domain.stone.StoneColor
import listener.OmokGameEventListener
import listener.OmokTurnEventListener
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokTest {
    private lateinit var blackRule: OmokRule
    private lateinit var whiteRule: OmokRule
    private lateinit var gameListener: OmokGameEventListener
    private lateinit var turnListener: OmokTurnEventListener

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
        )
    }

    @BeforeEach
    fun setUp() {
        blackRule = BlackRenjuRule()
        whiteRule = WhiteRenjuRule()

        gameListener = object : OmokGameEventListener {
            var curColor = StoneColor.WHITE
            override fun onStartTurn(stoneColor: StoneColor, point: Point?) {
                curColor = curColor.next()
                assertThat(stoneColor).isEqualTo(curColor)
            }

            override fun onEndTurn(players: Players) {}

            override fun onStartGame() {}

            override fun onEndGame(winnerStoneColor: StoneColor) {
                assertThat(winnerStoneColor).isEqualTo(StoneColor.BLACK)
            }
        }
        turnListener = object : OmokTurnEventListener {
            var round = 0
            override fun onTakeTurn(stoneColor: StoneColor): Point {
                return if (round % 2 == 0) {
                    round++
                    blackPositions.removeFirst()
                } else {
                    round++
                    whitePositions.removeFirst()
                }
            }

            override fun onNotPlaceable() {}
        }
    }

    @Test
    fun `차례마다 플레이어는 돌을 놓을 수 있고 바뀐 플레이어들 객체를 반환한다`() {
        val omok = Omok(turnListener, gameListener)
        val players = Players(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
        val actual = omok.takeTurn(players)
        assertThat(actual).usingRecursiveComparison().isNotEqualTo(players)
    }
}
