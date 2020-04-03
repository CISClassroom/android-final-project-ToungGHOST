package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main__page.*

class Main_Page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__page)

//
//        button8.setOnClickListener {
//            startActivity(Intent(this, Main4Activity::class.java))
//        }
        button7.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }



    }
}
