package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.model.board.BoardImpl
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.model.omokGame.OmokGameImpl
import woowacourse.omok.model.player.BlackPlayerState
import woowacourse.omok.model.player.Finish
import woowacourse.omok.model.player.PlayerState
import woowacourse.omok.model.player.WhitePlayerState
import woowacourse.omok.model.rule.OmokRuleAdapter
import woowacourse.omok.model.stone.StoneState
import woowacourse.omok.data.OmokDbController

class MainActivity : AppCompatActivity() {
    private lateinit var game: OmokGame
    private lateinit var playerState: PlayerState
    private lateinit var dbController: OmokDbController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUI()
        initializeGame()
        val board = savedGame()
        board
        .children
        .filterIsInstance<TableRow>()
        .forEachIndexed { rowIndex, tableRow ->
            tableRow.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, imageView ->
                imageView.tag = "$rowIndex,$colIndex"
                imageView.setOnClickListener {
                    val (row, col) = (it.tag as String).split(",").map { it.toInt() }
                    handleStoneClick(row, col, it)
                }
            }
        }
    }

    private fun handleStoneClick(row: Int, col: Int, view: View) {
        val position = Position(row + 1, col + 1)
        val imageStoneState = playerState

        try {
            dbController.saveStone(row + 1, col + 1, imageStoneState.stoneState().toString())

            playerState = playerState.state(position)
            dbController.saveTurn(playerState.stoneState().name)
            val resId = when (imageStoneState) {
                is BlackPlayerState -> R.drawable.black_stone
                is WhitePlayerState -> R.drawable.white_stone
                else -> return
            }
            (view as ImageView).setImageResource(resId)

            if (playerState is Finish) {
                val winner = (playerState as Finish).winner()
                Log.d("Omok", "게임 종료! 승자: $winner")

                val message = when (winner) {
                    StoneState.BLACK -> "흑돌이 이겼습니다!"
                    StoneState.WHITE -> "백돌이 이겼습니다!"
                    StoneState.NONE -> "무승부입니다!"
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                dbController.clearBoard()
            }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                this,
                e.message ?: "알 수 없는 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IllegalStateException) {
            Toast.makeText(this, e.message ?: "금수입니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun savedGame(): TableLayout {
        savedTurn()
        val board = findViewById<TableLayout>(R.id.board)
        savedStone(board)
        return board
    }

    private fun initializeGame() {
        dbController = OmokDbController(this)
        game = OmokGameImpl(BoardImpl.createEmpty(), OmokRuleAdapter())
        playerState = BlackPlayerState(game)
    }

    private fun setUpUI() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun savedStone(board: TableLayout?) {
        val savedStones = dbController.loadAllStones() // Triple<Int, Int, String>
        savedStones.forEach { (x, y, stoneString) ->
            val imageView = ((board?.getChildAt(x - 1) as TableRow).getChildAt(y - 1) as ImageView)
            val stoneState = StoneState.valueOf(stoneString)
            val resId = when (stoneState) {
                StoneState.BLACK -> R.drawable.black_stone
                StoneState.WHITE -> R.drawable.white_stone
                else -> null
            }
            resId?.let { imageView.setImageResource(it) }
        }
    }

    private fun savedTurn() {
        val savedTurn = dbController.loadTurn()
        playerState = when (savedTurn) {
            StoneState.WHITE.name -> WhitePlayerState(game)
            else -> BlackPlayerState(game)
        }
    }
}