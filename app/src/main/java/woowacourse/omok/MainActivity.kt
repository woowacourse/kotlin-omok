package woowacourse.omok

import RenjuRule
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.db.OmokDao
import woowacourse.omok.db.OmokEntity
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.Player
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones

class MainActivity : AppCompatActivity() {
    private val textView: TextView by lazy { findViewById(R.id.playerTurn_textView) }
    private val boardView: TableLayout by lazy { findViewById(R.id.board) }
    private lateinit var omokGame: OmokGame
    private val omokDao = OmokDao(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardSize = initOmokGame()
        playOmokGame(boardSize)
        setupResetButton()
    }

    private fun initOmokGame(): Int {
        val stones = createStones()
        drawStonesOnBoard(stones)
        val rule = createRule(stones)
        val boardSize = createBoard(stones, rule)
        return boardSize
    }

    private fun createStones(): Stones {
        val dbStones = omokDao.findAllOmok()
        val initialStones = mutableListOf<Stone>()
        dbStones.forEach { initialStones.add(getStoneFromDb(it)) }
        return Stones(initialStones)
    }

    private fun getStoneFromDb(entity: OmokEntity): Stone {
        val color =
            when (entity.stoneColor) {
                Color.BLACK.name -> Color.BLACK
                else -> Color.WHITE
            }
        val coordinate = Coordinate(entity.positionX, entity.positionY)
        return Stone(color, coordinate)
    }

    private fun drawStonesOnBoard(stones: Stones) {
        stones.stones.forEach {
            setStoneImage(
                it.coordinate.x - 1,
                it.coordinate.y - 1,
                getStoneImageResource(it.color),
            )
        }
    }

    private fun setStoneImage(
        x: Int,
        y: Int,
        resourceId: Int,
    ) {
        val row = boardView.getChildAt(x) as TableRow
        val imageView = row.getChildAt(y) as ImageView
        imageView.setImageResource(resourceId)
    }

    private fun getStoneImageResource(color: Color): Int {
        return when (color) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }
    }

    private fun createRule(stones: Stones): RenjuRule {
        return RenjuRule(stones)
    }

    private fun createBoard(
        stones: Stones,
        rule: RenjuRule,
    ): Int {
        val board = Board(stones, rule)
        omokGame = OmokGame(board)
        val player = omokGame.getCurrentPlayer()

        updateText("${player.color.name} 플레이어부터 시작합니다.")
        return board.width
    }

    private fun playOmokGame(boardSize: Int) {
        val board = getBoardImageViews()
        board.forEachIndexed { index, view ->
            val rowIndex = index / boardSize
            val columnIndex = index % boardSize

            view.setOnClickListener {
                val coordinate = Coordinate(rowIndex + 1, columnIndex + 1)
                val player = omokGame.getCurrentPlayer()
                progressGameTurn(player, coordinate) {
                    when (player.color) {
                        Color.BLACK -> view.setImageResource(R.drawable.black_stone)
                        Color.WHITE -> view.setImageResource(R.drawable.white_stone)
                    }
                }
            }
        }
    }

    private fun progressGameTurn(
        player: Player,
        coordinate: Coordinate,
        onPutStone: () -> Unit,
    ) {
        val stoneState =
            omokGame.playTurn(
                player = player,
                coordinate = coordinate,
                onPutStone,
            )
        when (stoneState) {
            is StoneState.SuccessfulPlaced -> {
                omokDao.insertOmok(
                    OmokEntity(
                        player.color.name,
                        coordinate.x,
                        coordinate.y,
                    ),
                )
                if (!omokGame.isRunning()) {
                    showToast(this, "${player.color}플레이어 승리!!!")
                    removeClickListeners()
                    return
                }
                updateText("${player.color}플레이어가 착수 했습니다.\n 마지막 돌의 위치: (${coordinate.x},${coordinate.y})")
            }

            is StoneState.FailedPlaced -> showToast(this, stoneState.message)
        }
    }

    private fun removeClickListeners() = getBoardImageViews().forEach { it.setOnClickListener(null) }

    private fun setupResetButton() {
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            resetBoard()
            omokDao.resetAll()
            val boardSize = initOmokGame()
            playOmokGame(boardSize)
        }
    }

    private fun resetBoard() = getBoardImageViews().forEach { it.setImageResource(0) }

    private fun getBoardImageViews(): Sequence<ImageView> {
        return boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
    }

    private fun updateText(newText: String) {
        textView.text = newText
    }

    private fun showToast(
        context: Context,
        message: String?,
    ) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
