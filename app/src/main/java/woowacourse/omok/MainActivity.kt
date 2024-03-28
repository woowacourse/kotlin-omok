package woowacourse.omok

import android.content.ContentValues
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.db.OmokContract
import woowacourse.omok.db.OmokDbHelper
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Point.Companion.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.model.WhiteTurn

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame()
    private val omokDb by lazy { OmokDbHelper(this).writableDatabase }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boardUi = findViewById<TableLayout>(R.id.board)
        boardUi
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { x, row ->
                row.children.filterIsInstance<ImageView>()
                    .forEachIndexed { y, view ->
                        view.setOnClickListener {
                            if (omokGame.isGameFinished()) {
                                displayMessage("게임이 종료되었습니다.")
                                return@setOnClickListener
                            }
                            progressGameTurn(view, x, y)
                        }
                    }
            }
    }

    private fun progressGameTurn(
        view: ImageView,
        x: Int,
        y: Int,
    ) {
        val isSuccess =
            omokGame.tryPlayTurn(
                updateTurn = {
                    view.setImageResource(getStoneImage(it.before?.type))
                    displayMessage(getTurnMessage(it))
                    it.before?.let { stone -> saveStoneData(stone) }
                    // omokDb.delete(OmokContract.TABLE_NAME, null, null)
                },
                getPoint = { Point(x, y) },
            )
        if (!isSuccess) {
            displayMessage(MESSAGE_INVALID_POINT_INPUT)
            return
        }
    }

    private fun getTurnMessage(turn: Turn): String =
        when (turn) {
            is BlackTurn -> "흑의 차례입니다."
            is WhiteTurn -> "백의 차례입니다."
            is FinishedTurn -> "게임이 종료되었습니다. ${getStoneTypeMessage(turn.before.type)}의 승리"
        }

    private fun getStoneTypeMessage(stoneType: StoneType): String {
        return when (stoneType) {
            StoneType.BLACK -> "흑"
            StoneType.WHITE -> "백"
            StoneType.EMPTY -> ""
        }
    }

    private fun getStoneImage(stoneType: StoneType?): Int {
        return when (stoneType) {
            StoneType.BLACK -> R.drawable.black_stone
            StoneType.WHITE -> R.drawable.white_stone
            StoneType.EMPTY -> 0
            null -> 0
        }
    }

    private fun saveStoneData(stone: Stone) {
        val newStone =
            ContentValues().apply {
                put(OmokContract.STONE_TYPE, stone.type.value)
                put(OmokContract.POINT_X, stone.point.x)
                put(OmokContract.POINT_Y, stone.point.y)
            }
        omokDb.insert(OmokContract.TABLE_NAME, null, newStone)
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
