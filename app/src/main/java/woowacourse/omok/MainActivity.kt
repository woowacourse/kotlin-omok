package woowacourse.omok

import RuleAdaptor
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
import woowacourse.omok.model.PositionX
import woowacourse.omok.model.PositionY
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones

class MainActivity : AppCompatActivity() {
    private val textView: TextView by lazy { findViewById(R.id.playerTurn_textView) }
    private val boardView: TableLayout by lazy { findViewById(R.id.board) }
    private val players = listOf(Player(Color.BLACK), Player(Color.WHITE))
    private var currentPlayerIndex = 0
    private lateinit var currentPlayer: Player
    private lateinit var omokGame: OmokGame
    private val omokDao = OmokDao(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupGame()
        setupOmokGame()
        playOmokGame()
        setupResetButton()
        setupBoardClickListeners()
    }

    private fun setupOmokGame() {
        val dbStones = omokDao.findAllOmok()
        val stonesList = mutableListOf<Stone>()

        dbStones.forEach {
            val color =
                when (it.stoneColor) {
                    Color.BLACK.name -> Color.BLACK
                    else -> Color.WHITE
                }
            val coordinate = Coordinate(PositionX(it.positionX), PositionY(it.positionY))
            stonesList.add(
                Stone(
                    color,
                    coordinate,
                ),
            )

            val resourceId =
                when (it.stoneColor) {
                    Color.BLACK.name -> R.drawable.black_stone
                    Color.WHITE.name -> R.drawable.white_stone
                    else -> 0
                }
            setStoneImage(coordinate.x.value - 1, coordinate.y.value - 1, resourceId)
        }

        currentPlayerIndex = stonesList.size
        currentPlayer = players[(currentPlayerIndex) % players.size]
        val board = Board(Stones(stonesList), RuleAdaptor(Stones(stonesList)))
        omokGame = OmokGame(board)

        updateText("${currentPlayer.color.name} 플레이어부터 시작합니다.")
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

    private fun playOmokGame() {
        boardView
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columnIndex, view ->
                        view.setOnClickListener {
                            val coordinate =
                                Coordinate(
                                    PositionX(rowIndex + 1),
                                    PositionY(columnIndex + 1),
                                )
                            progressGameTurn(currentPlayer, coordinate) {
                                when (currentPlayer.color) {
                                    Color.BLACK -> view.setImageResource(R.drawable.black_stone)
                                    Color.WHITE -> view.setImageResource(R.drawable.white_stone)
                                }
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
                        currentPlayer.color.name,
                        coordinate.x.value,
                        coordinate.y.value,
                    ),
                )
                if (!omokGame.isRunning()) {
                    showToast(this, "${currentPlayer.color}플레이어 승리!!!")
                    removeClickListeners()
                    return
                }
                changePlayerTurn(coordinate)
            }

            is StoneState.FailedPlaced -> showToast(this, stoneState.message)
        }
    }

    private fun removeClickListeners() {
        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setOnClickListener(null) }
    }

    private fun changePlayerTurn(coordinate: Coordinate) {
        updateText("${currentPlayer.color}플레이어가 착수 했습니다.\n 마지막 돌의 위치: (${coordinate.x.value},${coordinate.y.value})")
        currentPlayer = players[(currentPlayerIndex + 1) % players.size]
        currentPlayerIndex++
    }

    private fun resetBoard() {
        boardView
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setImageResource(0) }
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
