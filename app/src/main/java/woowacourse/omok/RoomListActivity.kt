package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RoomListActivity: AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_list)
        dbHelper = DbHelper(this)
        val db = dbHelper.writableDatabase

        findViewById<Button>(R.id.makeNewRoom).setOnClickListener {
            val nickname = intent.getStringExtra("nickname")

            val values = ContentValues()
            values.put("nickname", nickname)
            values.put("stone_count", 0)
            val newRowId = db.insert(RoomContract.TABLE_NAME, null, values)

            if (newRowId == -1L) {
                Log.e("MainActivity", "insert failed")
            } else {
                Log.d("MainActivity", "insert success: $newRowId")
            }
            db.close()

            startActivity(Intent(this,MainActivity::class.java).apply {
                putExtra("nickname",nickname)
            })
            finish()
        }
    }
}