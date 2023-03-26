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
import domain.player.WhitePlayer
import domain.point.Point
import domain.point.Points
import domain.result.TurnResult
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.state.PlayingState
import domain.stone.StoneColor
import woowacourse.omok.R
import woowacourse.omok.db.OmokDBManager
import woowacourse.omok.listener.GameEventListener

class MainActivity : AppCompatActivity() {
    private val dbManager by lazy { OmokDBManager(applicationContext) }
    private val omok: Omok by lazy { initOmok() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameEventListener =
            GameEventListener(applicationContext, findViewById(R.id.description))
        gameEventListener.onStartGame()
        gameEventListener.onStartTurn(omok.players.curPlayerColor, omok.players.getLastPoint())

        getBoardViews().forEachIndexed { index, view ->
            view.setOnClickListener {
                if (!omok.isPlaying) return@setOnClickListener
                val result = omok.takeTurn(calculateIndexToPoint(index))
                if (result is TurnResult.Success) {
                    setStone(view, omok.players.curPlayerColor.next())
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

    private fun setStone(view: ImageView, color: StoneColor) {
        when (color) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.pink_bear)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_bear)
        }
    }

    private fun initOmok(): Omok {
        val boardViews = getBoardViews()

        val blackIndexs = dbManager.getIndexsByColor(StoneColor.BLACK.name)
        val whiteIndexs = dbManager.getIndexsByColor(StoneColor.WHITE.name)

        blackIndexs.forEach {
            setStone(boardViews[it], StoneColor.BLACK)
        }
        whiteIndexs.forEach {
            setStone(boardViews[it], StoneColor.WHITE)
        }

        val blackPlayer =
            BlackPlayer(PlayingState(indexsToPoints(blackIndexs)), rule = BlackRenjuRule())
        val whitePlayer =
            WhitePlayer(PlayingState(indexsToPoints(whiteIndexs)), rule = WhiteRenjuRule())
        return Omok(blackPlayer, whitePlayer)
    }

    private fun getBoardViews(): List<ImageView> = findViewById<TableLayout>(R.id.board)
        .children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>().toList()

    private fun calculateIndexToPoint(index: Int): Point =
        Point(index / OMOK_BOARD_SIZE + 1, index % OMOK_BOARD_SIZE + 1)

    private fun indexsToPoints(indexs: List<Int>): Points =
        Points(indexs.map { calculateIndexToPoint(it) })
}
