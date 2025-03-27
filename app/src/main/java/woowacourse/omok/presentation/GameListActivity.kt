package woowacourse.omok.presentation

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameContract

class GameListActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_game_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)

        val games = queryGames()
        val gameAdapter =
            GameRecyclerAdapter(games) { gameId ->
                val intent =
                    Intent(this, MainActivity::class.java).apply {
                        putExtra("game_id", gameId.toLong())
                    }
                startActivity(intent)
            }

        findViewById<RecyclerView>(R.id.rv_game_list).apply {
            layoutManager = LinearLayoutManager(this@GameListActivity, RecyclerView.VERTICAL, false)
            adapter = gameAdapter
        }
    }

    private fun queryGames(): List<Pair<Int, String>> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Pair<Int, String>>()

        val cursor: Cursor =
            dbReader.query(
                GameContract.TABLE_NAME,
                arrayOf(
                    GameContract.COLUMN_NAME_GAME_ID,
                    GameContract.COLUMN_NAME_GAME_NAME,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val gameId = getLong(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_ID))
                val title = getString(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_NAME))
                result.add(Pair(gameId.toInt(), title))
            }
        }
        cursor.close()
        return result
    }
}
