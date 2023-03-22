package woowacourse.omok.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.game.Omok
import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.stone.StoneColor
import woowacourse.omok.listener.GameEventListener
import woowacourse.omok.R
import woowacourse.omok.listener.TurnEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val turnEventListener = TurnEventListener(applicationContext)
        val gameEventListener = GameEventListener(applicationContext, findViewById(R.id.description))
        val omok = Omok(turnEventListener, gameEventListener)
        var players = Players(BlackPlayer(rule = BlackRenjuRule()), WhitePlayer(rule = WhiteRenjuRule()))

        gameEventListener.onStartGame()
        gameEventListener.onStartTurn(players.curPlayerColor, players.getLastPoint())
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (players.isPlaying) {
                        turnEventListener.point = calculateIndexToPoint(index)
                        val endTurnPlayers = omok.takeTurn(players)
                        if (endTurnPlayers.isPut(players)) setStone(view, players)
                        players = endTurnPlayers
                        gameEventListener.onEndTurn(players)
                        omok.endGame(players)
                    }
                }
            }
    }

    private fun setStone(view: ImageView, players: Players) {
        when (players.curPlayerColor) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun calculateIndexToPoint(index: Int): Point = Point(index / OMOK_BOARD_SIZE + 1, index % OMOK_BOARD_SIZE + 1)
}
