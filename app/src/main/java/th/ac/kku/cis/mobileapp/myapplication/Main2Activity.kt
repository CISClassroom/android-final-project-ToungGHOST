package th.ac.kku.cis.mobileapp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mDatabase = FirebaseDatabase.getInstance().reference

        register_registerBtn.setOnClickListener {
            val name = editText.text.toString()
            val id = editText3.text.toString()
            val password1 = editText4.text.toString().trim { it <= ' ' }
            val password2 = editText5.text.toString().trim { it <= ' ' }
            val email1 = email_et.text.toString()
            if (id.isEmpty()) {
                editText3.error = "กรุณาใส่ผู้ใช้"
                return@setOnClickListener
            }
            if (password1.isEmpty()||password2.isEmpty()) {
                editText4.error = "กรุณาใส่รหัสผ่าน"
                editText5.error = "กรุณาใส่ยืนยันรหัสผ่าน"
                return@setOnClickListener
            }
            if (name.isEmpty()){
                editText.error = "กรุณาใส่ชื่อผู้ใช้"
                return@setOnClickListener
            }
            if (email1.isEmpty()){
                email_et.error = "กรุณาใส่ Email"
                return@setOnClickListener
            }
            mDatabase.child("student").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val student = dataSnapshot.children.iterator()
                    if(student.hasNext()){
                        var i=0
                        while (student.hasNext()){
                            val studentItem = student.next().getValue() as HashMap<String, Any>
                            if (studentItem.get("id") == id || studentItem.get("email") == email1){
                                i+=1
                            }
                        }
                        if(i==0){
                            if (password1==password2){
                                val student = th.ac.kku.cis.mobileapp.myapplication.student.create()
                                val newItem = mDatabase.child("student").push()
                                student.id=id
                                student.name=name
                                student.pass=password1
                                student.email=email1
                                student.objectId = newItem.key
                                student.sex = spinner.selectedItem.toString()
                                newItem.setValue(student)
                                Toast.makeText(this@Main2Activity,"ลงทะเบียนสำเร็จ!.", Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(this@Main2Activity,"กรุณาใส่รหัสให้ตรงกัน!", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@Main2Activity,"ชื่อผู้ใช หรือ E-mail มีการใช้งานแล้ว!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }
        val spinner: Spinner = findViewById(R.id.spinner)
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("ชาย")
        arrayList.add("หญิง")
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayList)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }
}


