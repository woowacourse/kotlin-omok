package woowacourse.omok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.AndroidView

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())
    private val dbHelper = OmokDbHelper(this)
    private lateinit var androidView: AndroidView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        androidView = AndroidView(this)

        restoreGame()
        androidView.setListeners(game.board) { position -> processTurn(position) }
        androidView.printOmokStart()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun restoreGame() {
        val stones: List<Stone> = dbHelper.queryAll()
        stones.forEach { stone ->
            game.play(stone)
            androidView.renderStone(game.board, stone)
        }
    }

    private fun processTurn(position: Position) {
        val color: Color = game.chooseTurn()
        val newStone = Stone(position, color)

        when (val moveResult: MoveResult = game.play(newStone)) {
            is MoveResult.Failure -> {
                androidView.printMoveResult(moveResult)
            }

            is MoveResult.Success.Playing -> {
                androidView.renderStone(game.board, newStone)
                dbHelper.insertData(position.x, position.y, color)
            }

            is MoveResult.Success.Finished -> {
                androidView.renderStone(game.board, newStone)
                dbHelper.insertData(position.x, position.y, color)
                androidView.printMoveResult(moveResult)
                dbHelper.clear()
                androidView.clearListeners()
                return
            }
        }
    }
}
