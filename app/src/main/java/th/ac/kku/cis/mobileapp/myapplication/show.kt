package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_show.*

class show : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference

    var topost: MutableList<posts>? = null
    lateinit var adapter: itemadapter_c
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        mDatabase = FirebaseDatabase.getInstance().reference





            var name = getIntent().getStringExtra("namep")
            var list = getIntent().getStringExtra("listp")
            var date = getIntent().getStringExtra("datep")
            var userp = getIntent().getStringExtra("userp")
            var objectId = getIntent().getStringExtra("objectId")

//        tv_name.text = name
            tv_id.text = name
            textView21.text = list
            tv_datep.text = date
            tv_user.text = userp

            btn_comment.setOnClickListener {
                val comments = et_comment.text.toString()

                mDatabase.child("comment").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val comment = dataSnapshot.children.iterator()
                        if(comment.hasNext()){
                            var i=0
                            while (comment.hasNext()){
                                val commentItem = comment.next().getValue() as HashMap<String, Any>
                                    val comment = th.ac.kku.cis.mobileapp.myapplication.comment.create()
                                    val newItem = mDatabase.child("comment").push()
                                    comment.commentp=comments
                                    comment.commentId=newItem.key
                                    comment.objectIdP=objectId

                                    newItem.setValue(comment)
                                    Toast.makeText(this@show,"คุณได้แสดงความคิดเห็นแล้ว!.", Toast.LENGTH_SHORT).show()
                                    finish()
                        }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            }

        }
    }



