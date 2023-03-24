package woowacourse.omok.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import domain.state.PlayingState
import domain.stone.StoneColor
import woowacourse.omok.listener.GameEventListener
import woowacourse.omok.R
import woowacourse.omok.db.OmokConstract
import woowacourse.omok.db.OmokDBHelper

class MainActivity : AppCompatActivity() {
    lateinit var omok: Omok
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blackPoints = mutableListOf<Point>()
        val whitePoints = mutableListOf<Point>()

        val db = OmokDBHelper(this).writableDatabase
        val cursor = db.query(
            OmokConstract.TABLE_NAME,
            arrayOf(
                OmokConstract.TABLE_COLUMN_POSITION,
                OmokConstract.TABLE_COLUMN_IS_BLACK,
            ),
            null,
            null,
            null,
            null,
            OmokConstract.TABLE_COLUMN_POSITION
        )
        val board = findViewById<TableLayout>(R.id.board)
        val boardSetting = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        while (cursor.moveToNext()) {
            val position =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_POSITION))
            val isBlack =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_IS_BLACK))
            if (isBlack == 1) {
                Log.d("isBLACK : ", OmokConstract.TABLE_COLUMN_POSITION)
                boardSetting[position].setImageResource(R.drawable.pink_bear)
                blackPoints.add(calculateIndexToPoint(position))
                continue
            }
            boardSetting[position].setImageResource(R.drawable.white_bear)
            whitePoints.add(calculateIndexToPoint(position))
        }

        val blackPlayer = BlackPlayer(PlayingState(Points(blackPoints)), rule = BlackRenjuRule())
        val whitePlayer = WhitePlayer(PlayingState(Points(whitePoints)), rule = BlackRenjuRule())
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
                        db.insert(
                            OmokConstract.TABLE_NAME,
                            null,
                            values(
                                index,
                                booleanToInt(omok.players.curPlayerColor.next() == StoneColor.BLACK)
                            )
                        )
                    }
                    gameEventListener.onEndTurn(result)
                    gameEventListener.onEndGame(omok.players)
                }
            }
    }

    override fun onStop() {
        super.onStop()
        val db = OmokDBHelper(this).writableDatabase
        if (!omok.isPlaying) db.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }

    private fun setStone(view: ImageView, players: Players) {
        when (players.curPlayerColor.next()) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.pink_bear)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_bear)
        }
    }

    private fun values(position: Int, isBlack: Int): ContentValues {
        val values = ContentValues()
        values.put(OmokConstract.TABLE_COLUMN_POSITION, position)
        values.put(OmokConstract.TABLE_COLUMN_IS_BLACK, isBlack)
        return values
    }

    private fun calculateIndexToPoint(index: Int): Point =
        Point(index / OMOK_BOARD_SIZE + 1, index % OMOK_BOARD_SIZE + 1)

    private fun calculatePointToIndex(point: Point?): Int =
        point?.let { (point.row - 1) * OMOK_BOARD_SIZE + point.col - 1 } ?: -1

    private fun booleanToInt(value: Boolean): Int {
        if (value) return 1
        return 0
    }
}

