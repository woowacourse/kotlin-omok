package woowacourse.omok

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import rule.BlackRenjuRule
import woowacourse.omok.adapter.RuleAdapter
import woowacourse.omok.database.OmokDao2
import woowacourse.omok.database.OmokDbHelper2
import woowacourse.omok.database.OmokEntity2
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.view.OmokView2

class RoomActivity : AppCompatActivity() {
    private val game = Game(Board(), RuleAdapter(BlackRenjuRule()))
    private lateinit var omokDao: OmokDao2
    private lateinit var boardLayout: TableLayout
    private lateinit var imageViews: Sequence<ImageView>
    private lateinit var omokView: OmokView2
    private lateinit var currentRoomName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        omokDao = OmokDao2(OmokDbHelper2(this))
        intent.getStringExtra("ROOM_NAME")?.let { currentRoomName = it }
        title = currentRoomName
        initializeRoom()
    }

    override fun onResume() {
        super.onResume()
        restoreRoom()
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_room -> {
                finish()
                true
            }
            R.id.action_delete_room -> {
                deleteCurrentRoom()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeRoom() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boardLayout = findViewById(R.id.board)
        imageViews =
            boardLayout.children
                .filterIsInstance<TableRow>()
                .flatMap { tableRow -> tableRow.children }
                .filterIsInstance<ImageView>()

        omokView = OmokView2()
        omokView.setListeners(imageViews, game.board) { position -> processTurn(position) }
        omokView.printOmokStart(boardLayout)
    }

    private fun deleteCurrentRoom() {
        AlertDialog.Builder(this)
            .setTitle(currentRoomName)
            .setMessage("현재 방을 삭제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                omokDao.clearRoom(currentRoomName)
                finish()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun restoreRoom() {
        val stones: List<Stone> = omokDao.queryByRoomName(currentRoomName).map { omokEntity -> omokEntity.toStone() }
        stones.forEach { stone ->
            processTurn(stone.position)
            omokView.renderStone(imageViews, game.board, stone)
        }
    }

    private fun processTurn(position: Position) {
        val color: Color = game.chooseTurn()
        val newStone = Stone(position, color)
        when (val moveResult: MoveResult = game.play(newStone)) {
            is MoveResult.Failure -> omokView.printMoveResult(this, boardLayout, moveResult)
            is MoveResult.Success.Playing -> processMove(newStone)
            is MoveResult.Success.Finished -> {
                processMove(newStone)
                finishGame(moveResult)
            }
        }
    }

    private fun processMove(newStone: Stone) {
        omokView.renderStone(imageViews, game.board, newStone)
        omokDao.insertData(newStone.toOmokEntity(currentRoomName))
    }

    private fun finishGame(moveResult: MoveResult.Success) {
        omokView.printMoveResult(this, boardLayout, moveResult)
        omokView.clearListeners(imageViews)
    }

    private fun Stone.toOmokEntity(roomName: String): OmokEntity2 {
        return OmokEntity2(position.x.value, position.y.value, color.name, roomName)
    }

    private fun OmokEntity2.toStone(): Stone {
        val color: Color =
            when (color) {
                Color.BLACK.name -> Color.BLACK
                Color.WHITE.name -> Color.WHITE
                else -> throw IllegalStateException()
            }
        return Stone(Position(Col(x), Row(y)), color)
    }
}
