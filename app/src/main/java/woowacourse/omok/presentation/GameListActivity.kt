package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameDao

class GameListActivity : AppCompatActivity() {
    private val dbHelper: DbHelper by lazy { DbHelper(this) }
    private val gameDao: GameDao by lazy { GameDao(dbHelper) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_game_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val games = gameDao.queryGames()
        val gameAdapter =
            GameRecyclerAdapter(games, onItemClick = { gameId ->
                val intent =
                    Intent(this, GameActivity::class.java).apply {
                        putExtra("game_id", gameId.toLong())
                    }
                startActivity(intent)
            }, onDelete = { gameId ->
                gameDao.deleteGame(gameId)
                Toast.makeText(this, "방이 삭제되었습니다.", Toast.LENGTH_LONG).show()
            })

        findViewById<RecyclerView>(R.id.rv_game_list).adapter = gameAdapter
    }
}
