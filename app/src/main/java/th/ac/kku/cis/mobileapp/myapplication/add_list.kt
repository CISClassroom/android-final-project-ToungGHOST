package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class add_list : AppCompatActivity() {
    private val TAG:String = "add activity"

    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)
        mDatabase = FirebaseDatabase.getInstance().reference

        val id = getIntent().getExtras()!!.getString("id")

        tv_userp.text = id

        button5.setOnClickListener {
            savedata()
        }
    }
    private fun savedata(){
        var name = name_et.text.toString().trim()
        var list = list_et.text.toString().trim()
        var dateT = date_et.text.toString().trim()
        var userp = tv_userp.text.toString().trim()



        if (name.isEmpty()){
            name_et.error = "กรุณาใสชื่อโพส"
            return
        }
        else if (list.isEmpty()) {
            list_et.error = "กรุณาใส่ลายระเอียด"
            return
        }else if (dateT.isEmpty()){
            date_et.error = "กรุณาใส่วันที่"
            return
        }
        var  todoItem = posts.create()
        val newItem = mDatabase.child("post").push()
        todoItem.objectId = newItem.key
        todoItem.namep = name
        todoItem.listp = list
        todoItem.datep = dateT
        todoItem.userp = userp
        newItem.setValue(todoItem)
        finish()
    }
}


