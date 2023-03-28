package woowacourse.omok

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val entryService: EntryService = EntryService(SQLiteEntryDao(this)).also {
        Log.d("MainActivity", "start ${(this as Context).hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate ${(this as Context).hashCode()}")
        val findAllByTitle = entryService.save("title", "title")
        Log.d("Database", "$findAllByTitle")

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }
    }

    override fun onDestroy() {
        val findAllByTitle = entryService.save("title", "title")
        Log.d("Database", "$findAllByTitle")
        super.onDestroy()
    }
}
