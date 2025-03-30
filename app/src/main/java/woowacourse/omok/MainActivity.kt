package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.OmokDaoImpl
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.Board.Companion.DEFAULT_BOARD_SIZE
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.rule.OmokRuleAdapter
import woowacourse.omok.domain.model.state.Finish
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.stone.StoneType

class MainActivity : AppCompatActivity() {
    private lateinit var boardUI: TableLayout
    private lateinit var omokDao: OmokDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUI()
        setupDao()
        val game = Game(OmokRuleAdapter())
        setupBoard(game)
        if (!omokDao.isGameFinished()) {
            restore(game)
        }
    }

    private fun setUI() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupDao() {
        val dbHelper = OmokDatabaseHelper(this)
        val database = dbHelper.writableDatabase
        omokDao = OmokDaoImpl(database)
    }

    private fun restore(game: Game) {
        val stones = omokDao.loadStones()
        stones.forEach { (position, stoneType) ->
            val imageView = boardUI.findViewWithTag<ImageView>(position)
            updateBoardUI(imageView, stoneType)
        }
        game.restoreGame(stones)
    }

    private fun setupBoard(game: Game) {
        boardUI = findViewById(R.id.board)
        boardUI.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
                val position = Position.of(colIndex, rowIndex, DEFAULT_BOARD_SIZE)
                imageView.tag = position
                imageView.setOnClickListener {
                    onStonePlaced(game, position)
                }
            }
        }
    }

    private fun onStonePlaced(
        game: Game,
        position: Position,
    ) {
        val imageView = boardUI.findViewWithTag<ImageView>(position)

        game.play(
            position = position,
            onPlace = { stoneType ->
                updateBoardUI(imageView, stoneType)
                omokDao.saveStone(position, stoneType)
            },
            onFailure = ::showToast,
            onFinish = { state ->
                omokDao.saveGameFinished(true)
                showGameResult(state)
            },
        )
    }

    private fun updateBoardUI(
        imageView: ImageView,
        stoneType: StoneType,
    ) {
        val stone =
            when (stoneType) {
                StoneType.BLACK -> R.drawable.black_stone
                StoneType.WHITE -> R.drawable.white_stone
                else -> return
            }

        imageView.setImageResource(stone)
    }

    private fun showGameResult(state: OmokState) {
        val message =
            when (state) {
                is Finish ->
                    if (state.winner == StoneType.NONE) {
                        getString(R.string.draw_message)
                    } else {
                        String.format(
                            getString(R.string.win_message),
                            state.winner.toKorean(),
                        )
                    }

                else -> return
            }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.exit_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.exit)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.restart)) { _, _ -> restart() }
            .setCancelable(false)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun restart() {
        omokDao.clearGameData()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun StoneType.toKorean(): String {
        return when (this) {
            StoneType.BLACK -> getString(R.string.black_stone)
            StoneType.WHITE -> getString(R.string.white_stone)
            else -> ""
        }
    }
}
