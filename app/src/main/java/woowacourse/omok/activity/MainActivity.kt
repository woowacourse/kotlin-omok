package woowacourse.omok.activity

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
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
import view.mapper.toPresentation
import woowacourse.omok.R
import woowacourse.omok.db.OmokDBHelper

class MainActivity : AppCompatActivity() {
    private val boards: List<ImageView> by lazy { getBoardViews() }
    private val descriptionView: TextView by lazy { findViewById(R.id.description) }
    private val dbHelper: OmokDBHelper by lazy { OmokDBHelper(applicationContext) }
    private val omok: Omok by lazy { initOmok() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onStartGame()
        onStartTurn(omok.players.curPlayerColor)

        var result: TurnResult = TurnResult.Playing(false, omok.players)
        boards.forEachIndexed { index, view ->
            view.setOnClickListener {
                if (result !is TurnResult.Playing) return@setOnClickListener
                result = omok.takeTurn(calculateIndexToPoint(index))
                onEndTurn(view, index, result)
                onEndGame(result)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (!omok.players.isPlaying) dbHelper.deleteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    private fun initOmok(): Omok {
        val blackIndexs = dbHelper.getIndexsByColor(StoneColor.BLACK.name)
        val whiteIndexs = dbHelper.getIndexsByColor(StoneColor.WHITE.name)

        blackIndexs.forEach {
            setStone(boards[it], StoneColor.BLACK)
        }
        whiteIndexs.forEach {
            setStone(boards[it], StoneColor.WHITE)
        }

        val blackPlayer = BlackPlayer(PlayingState(indexsToPoints(blackIndexs)), rule = BlackRenjuRule())
        val whitePlayer = WhitePlayer(PlayingState(indexsToPoints(whiteIndexs)), rule = WhiteRenjuRule())
        return Omok(blackPlayer, whitePlayer)
    }

    private fun onStartGame() {
        Toast.makeText(applicationContext, R.string.start_game, Toast.LENGTH_LONG).show()
    }

    private fun onEndGame(result: TurnResult) {
        val descriptionView = findViewById<TextView>(R.id.description)
        val context = applicationContext
        when (result) {
            is TurnResult.Playing -> return
            is TurnResult.Foul -> descriptionView.text = context.getString(R.string.is_forbidden).plus(context.getString(R.string.who_is_winner).format(result.winColor.toPresentation().text))
            is TurnResult.Win -> descriptionView.text = context.getString(R.string.who_is_winner).format(result.winColor.toPresentation().text)
        }
        Toast.makeText(context, R.string.end_game, Toast.LENGTH_LONG).show()
    }

    private fun onStartTurn(stoneColor: StoneColor) {
        val context = applicationContext
        descriptionView.text = context.getString(R.string.who_is_turn).format(stoneColor.toPresentation().text)
    }

    private fun setStone(view: ImageView, color: StoneColor) {
        when (color) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.pink_bear)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_bear)
        }
    }

    private fun onEndTurn(view: ImageView, index: Int, result: TurnResult) {
        val context = applicationContext
        if (result is TurnResult.Playing && result.isExistPoint) Toast.makeText(context, R.string.already_exist, Toast.LENGTH_LONG).show()
        if (result !is TurnResult.Playing || !result.isExistPoint) {
            setStone(view, omok.players.curPlayerColor.next())
            dbHelper.insert(index, omok.players.curPlayerColor.next().name)
        }
        descriptionView.text = context.getString(R.string.who_is_turn).format(result.players.curPlayerColor.toPresentation().text)
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
