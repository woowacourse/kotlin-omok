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
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.Player
import woowacourse.omok.model.PositionX
import woowacourse.omok.model.PositionY
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var viewBoard: TableLayout
    private val players = listOf(Player(Color.BLACK), Player(Color.WHITE))
    private var currentPlayerIndex = 0
    private lateinit var currentPlayer: Player
    private lateinit var omokGame: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
        initializeViews()
        setupGame()
        setupBoardClickListeners()
    }

    private fun initializeViews() {
        textView = findViewById(R.id.playerTurn_textView)
        viewBoard = findViewById(R.id.board)
    }

    private fun setupGame() {
        val stones = Stones()
        val board = Board(stones, RuleAdaptor(stones))
        currentPlayerIndex = 0
        currentPlayer = players[currentPlayerIndex]
        omokGame = OmokGame(board)

        updateText("BLACK 플레이어부터 시작합니다.")
    }

    private fun setupBoardClickListeners() {
        viewBoard
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }
            .forEachIndexed { rowIndex, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columnIndex, view ->
                        view.setOnClickListener {
                            val coordinate =
                                Coordinate(PositionX(rowIndex + 1), PositionY(columnIndex + 1))
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
                if (!omokGame.isRunning()) {
                    return
                }
                changePlayerTurn(coordinate)
            }

            is StoneState.FailedPlaced -> showToast(this, stoneState.message)
        }
    }
    private fun changePlayerTurn(coordinate: Coordinate) {
        updateText("${currentPlayer.color}플레이어가 착수 했습니다.\n 마지막 돌의 위치: (${coordinate.x.value},${coordinate.y.value})")

        currentPlayer = players[(currentPlayerIndex + 1) % players.size]
        currentPlayerIndex++
    }
    private fun updateText(newText: String) {
        textView.text = newText
    }
}
