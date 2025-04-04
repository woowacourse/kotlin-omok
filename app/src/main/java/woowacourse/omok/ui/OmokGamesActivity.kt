package woowacourse.omok.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.App
import woowacourse.omok.R
import woowacourse.omok.domain.Games
import woowacourse.omok.domain.event.GamesEvent
import woowacourse.omok.domain.event.GamesItemEvent
import woowacourse.omok.domain.model.Game
import woowacourse.omok.ui.IntentKeys.GAME_ID
import woowacourse.omok.ui.mapper.toGameUiModel

class OmokGamesActivity : AppCompatActivity() {
    private lateinit var games: Games
    private lateinit var omokGamesAdapter: OmokGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omok_games)
        val omokGamesView = findViewById<RecyclerView>(R.id.omok_games)
        omokGamesAdapter = OmokGamesAdapter(gamesItemEvent())
        omokGamesView.layoutManager = LinearLayoutManager(this)
        omokGamesView.adapter = omokGamesAdapter
        val addGameButton = findViewById<Button>(R.id.add_game)
        addGameButton.setOnClickListener {
            showCreateGameDialog { name -> games.insertGame(name) }
        }
        games =
            Games(
                (application as App).gameRepository, (application as App).stoneRepository,
                gamesEvent(),
            )
        games.updateGames()
    }

    private fun showCreateGameDialog(onCreate: (String) -> Unit) {
        val editText =
            EditText(this).apply {
                hint = getString(R.string.input_room_name)
                inputType = InputType.TYPE_CLASS_TEXT
            }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.make_room))
            .setView(editText)
            .setPositiveButton(getString(R.string.create_room_button)) { _, _ ->
                val name = editText.text.toString()
                onCreate(name)
                games.updateGames()
            }
            .setNegativeButton(getString(R.string.cancel_room_button), null)
            .show()
    }

    private fun gamesItemEvent() =
        object : GamesItemEvent {
            override fun onGame(id: Long) {
                val intent = Intent(this@OmokGamesActivity, MainActivity::class.java)
                intent.putExtra(GAME_ID, id)
                startActivity(intent)
            }

            override fun onDelete(id: Long) {
                games.deleteGame(id)
                games.updateGames()
            }
        }

    private fun gamesEvent() =
        object : GamesEvent {
            override fun updateGames(games: List<Game>) {
                omokGamesAdapter.submitList(games.map { it.toGameUiModel() })
            }
        }
}
