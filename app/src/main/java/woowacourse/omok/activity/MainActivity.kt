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
import domain.point.Points
import domain.result.TurnResult
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.state.PlayingState
import domain.stone.StoneColor
import woowacourse.omok.listener.GameEventListener
import woowacourse.omok.R
import woowacourse.omok.db.OmokDBManager

class MainActivity : AppCompatActivity() {
    private lateinit var omok: Omok
    private lateinit var dbManager: OmokDBManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbManager = OmokDBManager(applicationContext)

        val board = findViewById<TableLayout>(R.id.board)
        val boardViews = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        val blackIndexs = dbManager.getIndexsByColor("BLACK")
        val whiteIndexs = dbManager.getIndexsByColor("WHITE")

        blackIndexs.forEach {
            boardViews[it].setImageResource(R.drawable.pink_bear)
        }
        whiteIndexs.forEach {
            boardViews[it].setImageResource(R.drawable.white_bear)
        }

        val blackPlayer = BlackPlayer(PlayingState(Points(blackIndexs.map { calculateIndexToPoint(it) })), rule = BlackRenjuRule())
        val whitePlayer = WhitePlayer(PlayingState(Points(whiteIndexs.map { calculateIndexToPoint(it) })), rule = WhiteRenjuRule())
        omok = Omok(blackPlayer, whitePlayer)

        val gameEventListener =
            GameEventListener(applicationContext, findViewById(R.id.description))
        gameEventListener.onStartGame()
        gameEventListener.onStartTurn(omok.players.curPlayerColor, omok.players.getLastPoint())

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (!omok.isPlaying) return@setOnClickListener
                    val result = omok.takeTurn(calculateIndexToPoint(index))
                    if (result is TurnResult.Success) {
                        setStone(view, omok.players)
                        dbManager.insert(index, omok.players.curPlayerColor.next().name)
                    }
                    gameEventListener.onEndTurn(result)
                    gameEventListener.onEndGame(omok.players)
                }
            }
    }

    override fun onStop() {
        super.onStop()
        if (!omok.isPlaying) dbManager.deleteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
    private fun setStone(view: ImageView, players: Players) {
        when (players.curPlayerColor.next()) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.pink_bear)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_bear)
        }
    }

    private fun calculateIndexToPoint(index: Int): Point =
        Point(index / OMOK_BOARD_SIZE + 1, index % OMOK_BOARD_SIZE + 1)
}

