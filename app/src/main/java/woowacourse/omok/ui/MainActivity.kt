package woowacourse.omok.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.R
import woowacourse.omok.data.repository.RepositoryImpl
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.model.Board
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.GameState
import woowacourse.omok.model.state.Turn
import woowacourse.omok.utils.createVectorDrawable

class MainActivity : AppCompatActivity(), GamePlayHandler {
    private val gameManager = GameManager(gamePlayHandler = this, repository = RepositoryImpl(this))
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var allCoordinateViews: List<ImageView>
    private val blackStoneDrawable: Drawable? by lazy {
        createVectorDrawable(this, R.drawable.black_stone)
    }
    private val whiteStoneDrawable: Drawable? by lazy {
        createVectorDrawable(this, R.drawable.white_stone)
    }
    private val blockDrawable: Drawable? by lazy {
        createVectorDrawable(this, R.drawable.block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        gameManager.loadGame()
    }

    private fun initViews() {
        allCoordinateViews =
            binding.board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

        allCoordinateViews.forEachIndexed {
                index, view ->
            view.tag = NOT_PLACED
            view.setOnClickListener {
                gameManager.playTurn(index.toCoordinate())
            }
        }
    }

    override fun onUpdate(gameState: GameState) {
        showGameState(gameState)
        drawDiff(gameState.board)
    }

    private fun showGameState(gameState: GameState) {
        when (gameState) {
            is GameState.Playing.Start -> {
            }
            is GameState.Playing.Block -> {
                showGameSnackBar(R.string.block_position)
            }
            is GameState.Playing.Duplicate -> {
                showGameSnackBar(R.string.already_placed)
            }
            is GameState.Finish -> {
                showGameSnackBar(R.string.game_over, R.string.restart) {
                    resetViewTags()
                    gameManager.replay()
                }
            }
        }
    }

    private fun showGameSnackBar(msgResId: Int, actionMsgResId: Int? = null, action: (() -> Unit)? = null) {
        Snackbar.make(binding.root, msgResId, Snackbar.LENGTH_SHORT).apply {
            actionMsgResId?.let { setAction(it) { action?.invoke() } }
        }.show()
    }


    private fun drawDiff(board: Board) {
        board.boardLayout.flatten().forEachIndexed { index, coordinateState ->
            allCoordinateViews[index].apply {
                setImageDrawable(
                    when (coordinateState) {
                        is CoordinateState.Placed -> {
                            tag = PLACED
                            when (coordinateState.turn) {
                                is Turn.Black -> blackStoneDrawable
                                is Turn.White -> whiteStoneDrawable
                            }
                        }
                        is CoordinateState.Forbidden -> blockDrawable
                        is CoordinateState.Empty -> null
                    },
                )
            }
        }
    }

    private fun resetViewTags() {
        allCoordinateViews.forEach {
            it.tag = NOT_PLACED
        }
    }

    override fun onError(throwable: Throwable) {
        Snackbar.make(binding.root, throwable.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun Int.toCoordinate(): Coordinate = Coordinate(this / Board.BOARD_SIZE, this % Board.BOARD_SIZE)

    companion object {
        private const val PLACED = 1
        private const val NOT_PLACED = 0
    }
}
