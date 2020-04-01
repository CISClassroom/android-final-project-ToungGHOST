package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_list.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference
//        button4.setOnClickListener {
//            startActivity(
//                Intent(
//                    this, add_list::class.java
//                )
//            )
//        }

        button2.setOnClickListener {
            val id = user.text.toString().trim { it <= ' ' }
            val email = user.text.toString().trim { it <= ' ' }
            val password = pass.text.toString().trim { it <= ' ' }

            if (id.isEmpty()){
                user.error = "กรุณาใส่ผู้ใช้"
                return@setOnClickListener
            }
            if (email.isEmpty()){
                user.error = "กรุณาใส่ผู้ใช้"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                pass.error = "กรุณาใส่รหัสผ่าน"
                return@setOnClickListener
            }
            mDatabase.child("student")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val student = dataSnapshot.children.iterator()
                        var i=0


                        if(student.hasNext()){
                            while (student.hasNext()){
                                val studentItem = student.next().getValue() as HashMap<String, Any>
                                if (studentItem.get("id")==id || studentItem.get("email")==email && studentItem.get("pass") == password){
                                    i=0
                                    val intent = Intent(this@MainActivity, Main3Activity::class.java)
                                    intent.putExtra("id",studentItem.get("id") as String)
                                    intent.putExtra("email",studentItem.get("email") as String)
                                    startActivity(intent)
                                    user.text=null
                                    pass.text=null
                                    break
                                }else{
                                    i=1
                                }
                            }
                        }
                        if(i!=0 ){
                            user.error = "กรุณาใส่ชื่อผู้ใช้ให้ถูกต้อง"
                            pass.error = "กรุณาใส่รหัสผ่านให้ถูกต้อง"
                            return
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
        }
        button.setOnClickListener {
            startActivity(
                Intent(
                    this, Main2Activity::class.java
                )
            )
        }
    }
}