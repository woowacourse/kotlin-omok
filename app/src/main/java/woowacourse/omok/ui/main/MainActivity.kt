package woowacourse.omok.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.Exceptions
import woowacourse.omok.domain.exception.OmokException
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.service.OmokGame
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.ui.dialog.ConfirmDialog
import woowacourse.omok.ui.event.GameEventListener
import woowacourse.omok.ui.ext.showToast

class MainActivity : AppCompatActivity(), GameEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var game: OmokGame
    private lateinit var dataManager: OmokDataManager

    private var selectedImageView: ImageView? = null
    private val player by lazy { MediaController(this, R.raw.apple) }
    private var roomId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomId = intent.getLongExtra("roomId", 0)
        dataManager = OmokDataManager(this)

        initializeView()
        initializeSettings()
        restoreSavedStones(roomId)
    }

    override fun onMovedStone(stoneColor: StoneColor) {
        val stoneDrawable =
            when (stoneColor) {
                StoneColor.WHITE -> R.drawable.white_stone
                StoneColor.BLACK -> R.drawable.black_stone
            }

        selectedImageView?.setImageResource(stoneDrawable)
    }

    override fun onFinishedGame(color: StoneColor) {
        val stoneUiText = resolveStoneColorText(color)
        val winnerMessage = getString(R.string.text_winner, stoneUiText)
        ConfirmDialog(
            winnerMessage = winnerMessage,
            onClickFinish = { clear() },
            onClickRetry = { clear() },
        ).show(supportFragmentManager, "Main")
    }

    override fun onFailToAddStone(e: Exceptions) {
        val errorUiText = resolveErrorMessage(e)
        showToast(errorUiText)
    }

    override fun onPause() {
        super.onPause()
        game.getMovedStone().forEach {
            dataManager.saveStone(it, roomId)
        }
    }

    private fun initializeSettings() {
        game = OmokGame.create(this)

        binding.board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                setRowListener(row, rowIndex, game)
            }
    }

    private fun initializeView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player.play()
    }

    private fun resolveErrorMessage(e: Exceptions): String {
        val errorTextResource =
            when (e) {
                OmokException.OccupiedExceptions -> R.string.text_occupied
                RendjuException.DoubleFourException -> R.string.text_double_four
                RendjuException.DoubleThreeException -> R.string.text_double_three
                RendjuException.OverLineException -> R.string.text_over_line
                Exceptions.UnknownException -> R.string.text_unknown
            }

        return getString(errorTextResource)
    }

    private fun resolveStoneColorText(color: StoneColor): String {
        val colorTextResource =
            when (color) {
                StoneColor.WHITE -> R.string.text_white_stone
                StoneColor.BLACK -> R.string.text_black_stone
            }
        return getString(colorTextResource)
    }

    private fun setRowListener(
        row: TableRow,
        rowIndex: Int,
        game: OmokGame,
    ) {
        row.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, view ->
                val newTag = Coordination(Column(15 - rowIndex), Row(colIndex + 1))
                view.tag = newTag
                view.setOnClickListener {
                    selectedImageView = view
                    game.play(Point(x = newTag.x, y = newTag.y, BoardStatus.Empty))
                }
            }
    }

    private fun restoreSavedStones(roomId: Long) {
        val points = dataManager.loadSavedStones(roomId)
        if (points.isNotEmpty()) {
            drawSavedStone(points)
            game.combine(points)
            val nextTurn = if (points.size % 2 == 0) StoneColor.BLACK else StoneColor.WHITE
            game.setTurn(nextTurn)
        } else {
            game.setTurn(StoneColor.BLACK)
        }
    }

    private fun drawSavedStone(points: List<Point>) {
        points.forEach { drawStone(it, binding.board) }
    }

    private fun drawStone(
        point: Point,
        root: TableLayout,
    ) {
        val view = findStoneImageView(point, root) ?: return
        if (point.status is BoardStatus.Moved) {
            setStoneImage(view, point.status.color)
        }
    }

    private fun findStoneImageView(
        point: Point,
        root: TableLayout,
    ): ImageView? {
        return root.findViewWithTag(Coordination(point.x, point.y))
    }

    private fun setStoneImage(
        view: ImageView,
        color: StoneColor,
    ) {
        val stoneImgResource =
            when (color) {
                StoneColor.WHITE -> R.drawable.white_stone
                StoneColor.BLACK -> R.drawable.black_stone
            }
        view.setImageResource(stoneImgResource)
    }

    private fun clear() {
        dataManager.clearData()
        game.clear()
        binding.board
            .children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEach { it.setImageResource(0) }
            }
        game.setTurn(StoneColor.BLACK)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}
