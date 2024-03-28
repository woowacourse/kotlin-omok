package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.OmokStone
import omok.model.board.Board
import omok.model.game.InvalidGameRule
import omok.model.game.OmokGameResult
import omok.model.game.state.BlackTurn
import omok.model.game.state.GameState
import woowacourse.omok.console.view.ConsoleOmokOutputView
import woowacourse.omok.controller.OmokGameController
import woowacourse.omok.controller.toUiModel
import woowacourse.omok.model.PositionUiModel
import woowacourse.omok.model.StoneColorUiModel

class MainActivity : AppCompatActivity() {
    private var controller =
        OmokGameController(
            ConsoleOmokOutputView(),
            gameState = determineState(),
            onEndGame = ::onEndGame,
        )

    private lateinit var boardView: List<List<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate")
        initBoardView()
        initBoardClickListener()
        initResetButton()
    }

    private fun initBoardView() {
        boardView =
            findViewById<TableLayout>(R.id.board)
                .children
                .filterIsInstance<TableRow>()
                .map { it.children.filterIsInstance<ImageView>().toList() }
                .toList()
    }

    private fun initBoardClickListener() {
        boardView.forEachIndexed { x, row ->
            row.forEachIndexed { y, imageView ->
                val position = PositionUiModel(x + 1, y + 1)
                imageView.setOnClickListener {
                    controller.placeStone(
                        position,
                        onEndPlaced = { board, lastStone ->
                            onEndPlaced(imageView, board, lastStone)
                        },
                        onError = ::onError,
                    )
                }
            }
        }
    }

    private fun initResetButton() {
        findViewById<Button>(R.id.button_reset).setOnClickListener {
            recreate()
        }
    }

    private fun onEndGame(
        board: Board,
        lastStone: OmokStone,
    ) {
        val stoneColor = lastStone.toUiModel().stoneColor
        showToast("게임이 종료 되었습니다. ${stoneColor.format()} 승리!!")
        findViewById<TextView>(R.id.tv_winner).text = "${stoneColor.format()} 승리!!"
        boardView.forEach { row ->
            row.forEach { imageView ->
                imageView.setOnClickListener(null)
            }
        }
    }

    private fun onEndPlaced(
        imageView: ImageView,
        board: Board,
        lastStone: OmokStone,
    ) {
        val stoneColor = lastStone.toUiModel().stoneColor
        stoneColor.resource()
            .let(imageView::setImageResource)
    }

    private fun onError(result: OmokGameResult) {
        if (result is InvalidGameRule) {
            showToast("게임 룰 위반 다시 두세요")
        }
    }

    private fun determineState(): GameState = BlackTurn(Board())

    private fun StoneColorUiModel.resource(): Int {
        return when (this) {
            StoneColorUiModel.BLACK -> R.drawable.black_stone
            StoneColorUiModel.WHITE -> R.drawable.white_stone
            StoneColorUiModel.EMPTY -> throw IllegalArgumentException("빈 돌은 아직 이미지가 없습니다.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
