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

    var topost: MutableList<comment>? = null
    lateinit var adapter: itemadapter_cm
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)


        mDatabase = FirebaseDatabase.getInstance().reference
        listViewItems = findViewById<View>(R.id.listcomment_set) as ListView
        topost = mutableListOf<comment>()
        adapter = itemadapter_cm(this, topost!!)
        listViewItems!!.setAdapter(adapter)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        var objectId = getIntent().getStringExtra("objectId")
        var name = getIntent().getStringExtra("namep")
        var list = getIntent().getStringExtra("listp")
        var date = getIntent().getStringExtra("datep")
        var userp = getIntent().getStringExtra("userp")


        tv_name.text = name
//            tv_id.text = name
            textView21.text = list
            tv_datep.text = date
            tv_user.text = userp

            btn_comment.setOnClickListener {

                val comments = et_comment.text.toString()
                val comment_a = th.ac.kku.cis.mobileapp.myapplication.comment.create()
                if (comments != "" ){
                val newItem = mDatabase.child("comment").push()
                comment_a.commentp=comments
                comment_a.commentId=newItem.key
                comment_a.objectIdP=objectId
                val todoItem = comment.create()
                todoItem.commentp = comments
                todoItem.commentId = newItem.key
                todoItem.objectIdP =objectId
                topost!!.add(todoItem);
                newItem.setValue(comment_a)

                Toast.makeText(this@show,"คุณได้แสดงความคิดเห็นแล้ว!.", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                    et_comment.text.clear()
                }
            }
        }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            addDataToList(dataSnapshot.child("comment"))
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        var objectId = getIntent().getStringExtra("objectId")
        val items = dataSnapshot.children.iterator()
        // Check if current database contains any collection
        if (items.hasNext()) {
            // check if the collection has any to do items or not
            while (items.hasNext()) {
                // get current item
                val currentItem = items.next()
                val map = currentItem.getValue() as HashMap<String, Any>
                if(map.get("objectIdP") as String == objectId){
                    val todoItem = comment.create()
                    todoItem.commentp = map.get("commentp") as String
                    todoItem.commentId = map.get("commentId") as String
                    todoItem.objectIdP = map.get("objectIdP") as String
                    topost!!.add(todoItem);
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}





