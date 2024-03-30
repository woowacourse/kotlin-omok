package woowacourse.omok.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.R
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.model.Board
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.GameState
import woowacourse.omok.utils.createVectorDrawable

class MainActivity : AppCompatActivity(), GamePlayHandler {
    private val gameManager = GameManager(gamePlayHandler = this, context = this)
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var allPositions: List<ImageView>
    private val blackStoneDrawable: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.black_stone)
    }
    private val whiteStoneDrawable: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.white_stone)
    }
    private val blockDrawable: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initPosition()
        gameManager.loadGame()
    }

    // View의 역할
    private fun Int.toCoordinate(): Coordinate = Coordinate(this / Board.BOARD_SIZE, this % Board.BOARD_SIZE)

    override fun onUpdate(gameState: GameState) {
        val copiedBoard = gameState.board.boardLayout

        when (gameState) {
            is GameState.Playing.Start -> {
            }
            is GameState.Playing.Block -> {
                Snackbar.make(binding.root, R.string.block_position, Snackbar.LENGTH_SHORT).show()
            }
            is GameState.Playing.Duplicate -> {
                Snackbar.make(binding.root, R.string.already_placed, Snackbar.LENGTH_SHORT).show()
            }
            is GameState.Finish -> {
                Snackbar.make(binding.root, R.string.game_over, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.restart) {
                        resetPositionTag()
                        gameManager.replay()
                    }
                    .show()
            }
        }

        copiedBoard.flatten().forEachIndexed { index, positionType ->
            if (allPositions[index].tag == NOT_PLACED) { // 비어 있음. Block 포함. 놓은 곳은 굳이 다시 그리지 않게. 비어있는 곳은 X표시하게.
                allPositions[index].apply {
                    setImageDrawable(
                        when (positionType) {
                            CoordinateState.BlackStone -> {
                                tag = PLACED
                                blackStoneDrawable
                            }
                            CoordinateState.WhiteStone -> {
                                tag = PLACED
                                whiteStoneDrawable
                            }
                            CoordinateState.Forbidden -> {
                                blockDrawable
                            }
                            CoordinateState.Empty -> {
                                null
                            }
                        },
                    )
                }
            }
        }
    }

    override fun onError(throwable: Throwable) {
        Snackbar.make(binding.root, throwable.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun resetPositionTag() {
        allPositions.forEach {
            it.tag = NOT_PLACED
        }
    }

    private fun initPosition() {
        allPositions =
            binding.board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

        allPositions.forEachIndexed {
                index, view ->
            view.tag = NOT_PLACED
            view.setOnClickListener {
                gameManager.playTurn(index.toCoordinate())
            }
        }
    }

    companion object {
        private const val PLACED = 1
        private const val NOT_PLACED = 0
    }
}
