package woowacourse.omok

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GamePlayHandler {
    private val gameManager = GameManager(this)
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

    private fun createVectorDrawable(
        context: Context,
        vectorResId: Int,
    ): Drawable {
        return ContextCompat.getDrawable(context, vectorResId)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        allPositions =
            binding.board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

        allPositions.forEachIndexed {
                index, view ->
            view.setOnClickListener {
                gameManager.playTurn(index.toCoordinate())
            }
        }
    }

    // View의 역할
    private fun Int.toCoordinate(): Coordinate = Coordinate(this / Board.BOARD_SIZE, this % Board.BOARD_SIZE)

    override fun onDraw(gameState: GameState) {
        val copiedBoard = gameState.board.getBoardLayout()

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
                Snackbar.make(binding.root, R.string.game_over, Snackbar.LENGTH_SHORT).show()
            }
        }

        copiedBoard.flatten().forEachIndexed { index, positionType ->
            if (allPositions[index].tag == null) { // 비어 있음. Block 포함. 놓은 곳은 굳이 다시 그리지 않게. 비어있는 곳은 X표시하게.
                when (positionType) {
                    CoordinateState.BlackStone -> {
                        allPositions[index].apply {
                            tag = 1
                            setImageDrawable(blackStoneDrawable)
                        }
                    }
                    CoordinateState.WhiteStone -> {
                        allPositions[index].apply {
                            tag = 1
                            setImageDrawable(whiteStoneDrawable)
                        }
                    }
                    CoordinateState.Forbidden -> {
                        allPositions[index].apply {
                            setImageDrawable(blockDrawable)
                        }
                    }
                    CoordinateState.Empty -> {
                        allPositions[index].apply {
                            setImageDrawable(null)
                        }
                    }
                }
            }
        }
    }
}
