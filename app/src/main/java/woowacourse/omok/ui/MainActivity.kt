package woowacourse.omok.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.data.datasource.OmokDataSource
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.repository.OmokRepositoryImpl
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Coordination
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.Exceptions
import woowacourse.omok.domain.exception.OmokExceptions
import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.point.OmokPoints
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.repository.OmokRepository
import woowacourse.omok.domain.service.OmokGame
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.ui.event.GameEventListener

class MainActivity : AppCompatActivity(), GameEventListener {
    private lateinit var board: TableLayout
    private lateinit var game: OmokGame
    private lateinit var omokRepository: OmokRepository
    private var selectedImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = OmokDbHelper(this)
        val omokDataSource = OmokDataSource(dbHelper)
        omokRepository = OmokRepositoryImpl(omokDataSource)
        game = OmokGame(OmokBoard(OmokPoints()), this)
        board = findViewById(R.id.board)

        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                setRowListener(row, rowIndex, game)
            }

        restoreSavedStones()
    }

    override fun onPlacedStone(stoneColor: StoneColor) {
        val stoneDrawable =
            when (stoneColor) {
                StoneColor.WHITE -> R.drawable.white_stone
                StoneColor.BLACK -> R.drawable.black_stone
            }

        selectedImageView?.setImageResource(stoneDrawable)
    }

    override fun onFinishedGame(color: StoneColor) {
        val stoneUiText = resolveStoneColorText(color)
        val uiText = getString(R.string.text_winner, stoneUiText)
        ConfirmDialog(
            winnerMessage = uiText,
            onClickFinish = {
            },
            onClickRetry = {
            },
        ).show(supportFragmentManager, "Main")
    }

    override fun onFailToAddStone(e: Exceptions) {
        val errorUiText = resolveErrorMessage(e)
        Toast.makeText(this, errorUiText, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        game.getMovedStone().forEach {
            omokRepository.saveNewPoint(it)
        }
    }

    private fun resolveErrorMessage(e: Exceptions): String {
        val errorTextResource =
            when (e) {
                is OmokExceptions.OccupiedExceptions -> R.string.text_occupied
                RendjuExceptions.DoubleFourExceptions -> R.string.text_double_four
                RendjuExceptions.DoubleThreeExceptions -> R.string.text_double_three
                RendjuExceptions.OverLineExceptions -> R.string.text_over_line
                Exceptions.UnknownException -> R.string.text_unknown
            }

        val errorUiText = getString(errorTextResource)
        return errorUiText
    }

    private fun resolveStoneColorText(color: StoneColor): String {
        val colorTextResource =
            when (color) {
                StoneColor.WHITE -> R.string.text_white_stone
                StoneColor.BLACK -> R.string.text_black_stone
            }
        val colorUiText = getString(colorTextResource)
        return colorUiText
    }

    private fun setRowListener(
        row: TableRow,
        rowIndex: Int,
        game: OmokGame,
    ) {
        row.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, view ->
                val newTag =
                    Coordination(Column(15 - rowIndex), Row(colIndex + 1))
                view.tag = newTag
                view.setOnClickListener {
                    selectedImageView = view
                    game.play(Point(x = newTag.x, y = newTag.y, BoardStatus.Empty))
                }
            }
    }

    private fun restoreSavedStones() {
        val points = omokRepository.readAllPoint()
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
        val root = findViewById<LinearLayout>(R.id.board)
        points.forEach { drawStone(it, root) }
    }

    private fun drawStone(
        point: Point,
        root: LinearLayout,
    ) {
        val view = findStoneImageView(point, root) ?: return
        if (point.status is BoardStatus.Moved) {
            setStoneImage(view, point.status.color)
        }
    }

    private fun findStoneImageView(
        point: Point,
        root: LinearLayout,
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
}
