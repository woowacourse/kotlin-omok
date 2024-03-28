package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omock.model.position.Column
import omock.model.position.Row

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed{ rowIndex , rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { columIndex, view ->
                        view.setOnClickListener {
                            Log.d("dlajls",Column.transformIndex(columIndex))
                            Log.d("sdlfjsld",(Row.transformIndex(rowIndex)))
                            view.setImageResource(R.drawable.black_stone)
                        }
                    }
            }

    }
}
