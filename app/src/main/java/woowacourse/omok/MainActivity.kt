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

    private lateinit var allImageViews: List<ImageView>

    private val blackStoneImage: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.black_stone)
    }

    private val whiteStoneImage: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.white_stone)
    }

    private val blockImage: Drawable by lazy {
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

        allImageViews =
            binding.board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

        allImageViews.forEachIndexed {
                index, view ->
            view.setOnClickListener {
                gameManager.playTurn(index.toCoordinate())
            }
        }
    }

    // View의 역할
    private fun Int.toCoordinate(): Coordinate = Coordinate(this / 15, this % 15)

    override fun onDraw(gameState: GameState) {
        val copiedBoard = gameState.board.getBoardLayout()

        when (gameState) {
            is GameState.Running.BlackTurn.Block -> {
                Snackbar.make(binding.root, "금수의 위치입니다.", Snackbar.LENGTH_SHORT).show()
            }
            is GameState.Running.BlackTurn.Duplicate -> {
                Snackbar.make(binding.root, "이미 돌이 놓인 자리입니다.", Snackbar.LENGTH_SHORT).show()
            }
            is GameState.Running.WhiteTurn.Duplicate -> {
                Snackbar.make(binding.root, "이미 돌이 놓인 자리입니다.", Snackbar.LENGTH_SHORT).show()
            }
            is GameState.Finish -> {
                Snackbar.make(binding.root, "게임이 종료되었습니다.", Snackbar.LENGTH_SHORT).show()
            }
            else -> {
            }
        }

        copiedBoard.flatten().forEachIndexed { index, positionType ->
            if (allImageViews[index].tag == null) { // 비어 있음. Block 포함. 놓은 곳은 굳이 다시 그리지 않게. 비어있는 곳은 X표시하게.
                when (positionType) {
                    PositionType.BLACK_STONE -> {
                        allImageViews[index].apply {
                            tag = 1
                            setImageDrawable(blackStoneImage)
                        }
                    }
                    PositionType.WHITE_STONE -> {
                        allImageViews[index].apply {
                            tag = 1
                            setImageDrawable(whiteStoneImage)
                        }
                    }
                    PositionType.BLOCK -> {
                        allImageViews[index].apply {
                            setImageDrawable(blockImage)
                        }
                    }
                    PositionType.EMPTY -> {
                        allImageViews[index].apply {
                            setImageDrawable(null)
                        }
                    }
                }
            }
        }
    }
}
