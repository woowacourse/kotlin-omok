package woowacourse.omok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.database.OmokDao
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.database.OmokEntity
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.OmokView

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())
    private val omokDao = OmokDao(OmokDbHelper(this))
    private lateinit var omokView: OmokView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        omokView = OmokView(this)

        restoreGame()
        omokView.setListeners(game.board) { position -> processTurn(position) }
        omokView.printOmokStart()
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun restoreGame() {
        val stones: List<Stone> = omokDao.queryAll().map { omokEntity -> omokEntity.toStone() }
        stones.forEach { stone ->
            game.play(stone)
            omokView.renderStone(game.board, stone)
        }
    }

    private fun processTurn(position: Position) {
        val color: Color = game.chooseTurn()
        val newStone = Stone(position, color)
        when (val moveResult: MoveResult = game.play(newStone)) {
            is MoveResult.Failure -> omokView.printMoveResult(moveResult)
            is MoveResult.Success.Playing -> processMove(newStone)
            is MoveResult.Success.Finished -> {
                processMove(newStone)
                finishGame(moveResult)
            }
        }
    }

    private fun processMove(newStone: Stone) {
        omokView.renderStone(game.board, newStone)
        omokDao.insertData(newStone.toOmokEntity())
    }

    private fun finishGame(moveResult: MoveResult) {
        omokView.printMoveResult(moveResult)
        omokDao.clear()
        omokView.clearListeners()
    }

    private fun Stone.toOmokEntity(): OmokEntity {
        return OmokEntity(position.x.value, position.y.value, color.name)
    }

    private fun OmokEntity.toStone(): Stone {
        val color: Color =
            when (color) {
                Color.BLACK.name -> Color.BLACK
                Color.WHITE.name -> Color.WHITE
                else -> throw IllegalStateException()
            }
        return Stone(Position(Col(x), Row(y)), color)
    }
}
