package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import woowacourse.omok.model.database.OmokDBHelper
import woowacourse.omok.model.database.PlayerInfo
import woowacourse.omok.model.gameRoom.GameRoom
import woowacourse.omok.model.gameRoom.GameRoomAdapter
import woowacourse.omok.view.MainActivityInputView

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
                showNewGameAddedGameRoomList(blackName, whiteName)
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

    private fun showNewGameAddedGameRoomList(
        blackStoneName: String,
        whiteStoneName: String,
    ) {
        val newGameRoom = dbHelper.fetchNewGameRoom(blackStoneName, whiteStoneName)
        newGameRoom?.let {
            gameRooms.add(0, newGameRoom)
            gameRoomAdapter.notifyItemInserted(0)
            recyclerView.scrollToPosition(0)
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

    private fun showInfoDialog() {
        val input =
            EditText(this).apply {
                hint = getString(R.string.game_record_info_dialog_input_player_name_hint)
                inputType = InputType.TYPE_CLASS_TEXT
            }

        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.game_record_info_dialog_title))
            .setView(input)
            .setPositiveButton(getString(R.string.ok_button)) { dialog, _ ->
                handlePlayerSearch(input.text.toString())
                dialog.dismiss()
            }.setNegativeButton(getString(R.string.cancel_button)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun handlePlayerSearch(playerName: String) {
        if (playerName.isEmpty()) {
            Toast.makeText(this, getString(R.string.alert_no_input), Toast.LENGTH_SHORT).show()
            return
        }

        val playerInfo = dbHelper.getPlayerInfo(playerName)
        if (playerInfo != null) {
            showPlayerStatsDialog(playerInfo)
        } else {
            Toast
                .makeText(
                    this,
                    getString(R.string.alert_no_history, playerName),
                    Toast.LENGTH_SHORT,
                ).show()
        }
    }

    fun showPlayerStatsDialog(playerInfo: PlayerInfo) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.info_dialog_show_history_title, playerInfo.name))
        builder.setMessage(
            """
            ${getString(R.string.number_of_matches, playerInfo.playCount)}
            ${getString(R.string.number_of_black_wins, playerInfo.blackWinCount)}
            ${getString(R.string.number_of_white_wins, playerInfo.whiteWinCount)}
            """.trimIndent(),
        )
        builder.setPositiveButton(getString(R.string.ok_button)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }

    companion object {
        private const val GAME_ROOM_ID = "game_room_id"
        private const val BLACK_PLAYER = "black_player"
        private const val WHITE_PLAYER = "white_player"
    }
}
