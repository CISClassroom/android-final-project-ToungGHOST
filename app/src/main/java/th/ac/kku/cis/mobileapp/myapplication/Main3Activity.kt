package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val id = getIntent().getExtras()!!.getString("id")
        val email = getIntent().getExtras()!!.getString("email")

        text_user.text=id
        text_eamil.text=email



        button3.setOnClickListener {
            val intent = Intent(this@Main3Activity, add_list::class.java)
            intent.putExtra("id",id.toString())
            startActivity(intent)
        }
        button4.setOnClickListener {
            startActivity(Intent(this, Main4Activity::class.java))
        }
        logout_bt.setOnClickListener {
            startActivity(Intent(this, Main_Page::class.java))
        }


    }
}
