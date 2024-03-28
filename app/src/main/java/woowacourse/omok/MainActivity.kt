package woowacourse.omok

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import woowacourse.omok.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GamePlayHandler {
    val gameManager = GameManager(this)
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var allImageViews: List<ImageView>

    val blackStoneImage: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.black_stone)
    }

    val whiteStoneImage: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.white_stone)
    }

    val blockImage: Drawable by lazy {
        createVectorDrawable(applicationContext, R.drawable.block)
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
    fun Int.toCoordinate(): Coordinate = Coordinate(this / 15, this % 15)

    override fun onDraw(board: Board) {
        val copiedBoard = board.getBoardLayout()

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

    fun createVectorDrawable(
        context: Context,
        vectorResId: Int,
    ): Drawable {
        return ContextCompat.getDrawable(context, vectorResId)!!
    }
}
