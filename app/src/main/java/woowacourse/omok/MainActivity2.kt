package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import rule.BlackRenjuRule
import woowacourse.omok.adapter.RuleAdapter
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
import woowacourse.omok.view.OmokView2

class MainActivity2 : AppCompatActivity() {
    private val game = Game(Board(), RuleAdapter(BlackRenjuRule()))
    private val omokDao = OmokDao(OmokDbHelper(this))
    private lateinit var boardLayout: TableLayout
    private lateinit var imageViews: Sequence<ImageView>
    private lateinit var omokView: OmokView2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boardLayout = findViewById(R.id.board)
        imageViews =
            boardLayout.children
                .filterIsInstance<TableRow>()
                .flatMap { tableRow -> tableRow.children }
                .filterIsInstance<ImageView>()

        omokView = OmokView2()

        restoreGame()
        omokView.setListeners(imageViews, game.board) { position -> processTurn(position) }
        omokView.printOmokStart(boardLayout)
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun restoreGame() {
        val stones: List<Stone> = omokDao.queryAll().map { omokEntity -> omokEntity.toStone() }
        stones.forEach { stone ->

            game.play(stone)
            omokView.renderStone(imageViews, game.board, stone)
        }
    }

    private fun processTurn(position: Position) {
        val color: Color = game.chooseTurn()
        val newStone = Stone(position, color)
        when (val moveResult: MoveResult = game.play(newStone)) {
            is MoveResult.Failure -> omokView.printMoveResult(this, boardLayout, moveResult)
            is MoveResult.Success.Playing -> processMove(newStone)
            is MoveResult.Success.Finished -> {
                processMove(newStone)
                finishGame(moveResult)
            }
        }
    }

    private fun processMove(newStone: Stone) {
        omokView.renderStone(imageViews, game.board, newStone)
        omokDao.insertData(newStone.toOmokEntity())
    }

    private fun finishGame(moveResult: MoveResult) {
        omokView.printMoveResult(this, boardLayout, moveResult)
        omokDao.clear()
        omokView.clearListeners(imageViews)
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
