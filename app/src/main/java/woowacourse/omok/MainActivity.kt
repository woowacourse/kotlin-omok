package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import omok.model.board.BoardImpl
import omok.model.board.Position
import omok.model.omokGame.OmokGame
import omok.model.omokGame.OmokGameImpl
import omok.model.player.BlackPlayerState
import omok.model.player.Finish
import omok.model.player.PlayerState
import omok.model.player.WhitePlayerState
import omok.model.rule.OmokRuleAdapter
import omok.model.stone.StoneState

class MainActivity : AppCompatActivity() {
    private lateinit var game: OmokGame
    private lateinit var playerState: PlayerState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        game = OmokGameImpl(BoardImpl.createEmpty(), OmokRuleAdapter())
        playerState = BlackPlayerState(game)

        val board = findViewById<TableLayout>(R.id.board)
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
                            val position = Position(row + 1, col + 1)

                            val imageStoneState = playerState

                            try {
                                playerState = playerState.state(position)

                                val resId = when (imageStoneState) {
                                    is BlackPlayerState -> R.drawable.black_stone
                                    is WhitePlayerState -> R.drawable.white_stone
                                    else -> return@setOnClickListener
                                }
                                (it as ImageView).setImageResource(resId)

                                if (playerState is Finish) {
                                    val winner = (playerState as Finish).winner()
                                    Log.d("Omok", "게임 종료! 승자: $winner")

                                    val message = when (winner) {
                                        StoneState.BLACK -> "흑돌이 이겼습니다!"
                                        StoneState.WHITE -> "백돌이 이겼습니다!"
                                        StoneState.NONE -> "무승부입니다!"
                                    }
                                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
                    }
            }
    }
}

