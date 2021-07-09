package android.kimpleapp.kimple_kotlin_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View?) {
        val i = Intent(this@MainActivity, WebviewActivity::class.java)
        val hash = findViewById<EditText>(R.id.hashid)
        val hashId: String = hash.getText().toString()
        i.putExtra("hashId", hashId)
        startActivity(i)
    }
}