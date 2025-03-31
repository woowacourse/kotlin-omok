package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import woowacourse.omok.model.database.OmokDBContract
import woowacourse.omok.model.database.OmokDBHelper
import woowacourse.omok.model.database.PlayerInfo
import woowacourse.omok.model.gameRoom.GameRoom
import woowacourse.omok.model.gameRoom.GameRoomAdapter
import woowacourse.omok.view.MainActivityInputView
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerview_game_rooms) }
    private val createGameButton: ExtendedFloatingActionButton by lazy { findViewById(R.id.new_game_btn) }
    private val dbHelper: OmokDBHelper by lazy { OmokDBHelper(this) }
    private lateinit var gameRoomAdapter: GameRoomAdapter

    private val gameRooms = mutableListOf<GameRoom>()

    private val gameActivityLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                reloadPreviousGameRooms()
                gameRoomAdapter.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityInputView = MainActivityInputView(this)

        setupListeners(mainActivityInputView)
        reloadPreviousGameRooms()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        gameRoomAdapter =
            GameRoomAdapter(
                gameRooms,
                onItemClick = { gameRoom -> openGameRoom(gameRoom) },
            )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameRoomAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners(mainActivityInputView: MainActivityInputView) {
        createGameButton.setOnClickListener {
            mainActivityInputView.showPlayerNamesInputDialog { blackName, whiteName ->
                dbHelper.addPlayerHistory(blackName, playCount = 1)
                dbHelper.addPlayerHistory(whiteName, playCount = 1)
                showCreateGameDialog(blackName, whiteName)
            }
        }
    }

    private fun reloadPreviousGameRooms() {
        gameRooms.clear()
        gameRooms += dbHelper.fetchDBGameRooms()
    }

    private fun openGameRoom(gameRoom: GameRoom) {
        val intent =
            Intent(this, GameActivity::class.java).apply {
                putExtra(GAME_ROOM_ID, gameRoom.id)
                putExtra(BLACK_PLAYER, gameRoom.blackStonePlayerName)
                putExtra(WHITE_PLAYER, gameRoom.whiteStonePlayerName)
            }
        gameActivityLauncher.launch(intent)
    }

    private fun showCreateGameDialog(
        blackStoneName: String,
        whiteStoneName: String,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME, blackStoneName)
                put(OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME, whiteStoneName)
            }

        val newRoomId = db.insert(OmokDBContract.GameRoomsTable.TABLE_NAME, null, values)

        val projection =
            arrayOf(
                OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID,
                OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME,
                OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME,
            )

        val selection = "${OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID} = ?"
        val selectionArgs = arrayOf(newRoomId.toString())

        db
            .query(
                OmokDBContract.GameRoomsTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    val blackPlayerName =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_BLACK_PLAYER_NAME))
                    val whitePlayerName =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_WHITE_PLAYER_NAME))
                    val lastPlayTime =
                        cursor.getString(cursor.getColumnIndexOrThrow(OmokDBContract.GameRoomsTable.COLUMN_LAST_PLAY_TIME))

                    val newGameRoom =
                        GameRoom(
                            id = newRoomId.toInt(),
                            blackStonePlayerName = blackPlayerName,
                            whiteStonePlayerName = whitePlayerName,
                            lastPlayTime = LocalDateTime.parse(lastPlayTime, OmokDBContract.dbTimeFormatter),
                        )

                    gameRooms.add(0, newGameRoom)
                    gameRoomAdapter.notifyItemInserted(0)
                    recyclerView.scrollToPosition(0)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.player_info, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_info -> {
                showInfoDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    fun showInfoDialog() {
        val input = EditText(this)
        input.hint = "기록을 확인할 닉네임을 입력하세요"

        val builder = AlertDialog.Builder(this)
        builder.setTitle("오목 게임 기록")
        builder.setView(input)

        builder.setPositiveButton("확인") { dialog, _ ->
            val userInput = input.text.toString()
            if (userInput.isNotEmpty()) {
                val playerInfo = dbHelper.getPlayerInfo(userInput)
                if (playerInfo != null) {
                    showPlayerStatsDialog(playerInfo)
                } else {
                    Toast.makeText(this, "${userInput}의 기록은 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                }
                dbHelper.getPlayerInfo(userInput)
            } else {
                Toast.makeText(this, "값을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    fun showPlayerStatsDialog(playerInfo: PlayerInfo) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("${playerInfo.name}님의 기록")
        builder.setMessage(
            """
            대국 횟수: ${playerInfo.playCount}
            흑돌 승리 횟수: ${playerInfo.blackWinCount}
            백돌 승리 횟수: ${playerInfo.whiteWinCount}
            """.trimIndent(),
        )
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }

    companion object {
        const val GAME_ROOM_ID = "game_room_id"
        const val BLACK_PLAYER = "black_player"
        const val WHITE_PLAYER = "white_player"
    }
}
