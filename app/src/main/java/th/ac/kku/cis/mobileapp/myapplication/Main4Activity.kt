package th.ac.kku.cis.mobileapp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference

    var topost: MutableList<posts>? = null
    lateinit var adapter: itemadapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        mDatabase = FirebaseDatabase.getInstance().reference


        listViewItems = findViewById<View>(R.id.listview) as ListView
        topost = mutableListOf<posts>()
        adapter = itemadapter(this, topost!!)
        listViewItems!!.setAdapter(adapter)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        listview.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, show::class.java)
            val selectedItem = parent.getItemAtPosition(position) as posts
            intent.putExtra("namep", selectedItem.namep.toString())
            intent.putExtra("listp", selectedItem.listp.toString())
            intent.putExtra("datep", selectedItem.datep.toString())
            intent.putExtra("userp", selectedItem.userp.toString())
            intent.putExtra("objectId", selectedItem.objectId.toString())
            startActivity(intent)


        }
    }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            addDataToList(dataSnapshot.child("post"))
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        // Check if current database contains any collection
        if (items.hasNext()) {


            // check if the collection has any to do items or not
            while (items.hasNext()) {
                // get current item
                val currentItem = items.next()
                val map = currentItem.getValue() as HashMap<String, Any>
                // add data to object

                val todoItem = posts.create()
                todoItem.namep = map.get("namep") as String
                todoItem.listp = map.get("listp") as String
                todoItem.datep = map.get("datep") as String
                todoItem.userp = map.get("userp") as String
                todoItem.objectId = map.get("objectId") as String
                topost!!.add(todoItem);

            }

            adapter.notifyDataSetChanged()
        }
    }
}

