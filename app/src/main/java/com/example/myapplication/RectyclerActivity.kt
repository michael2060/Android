package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.android.synthetic.main.activity_recyclerview.*
import timber.log.Timber

class RectyclerActivity : AppCompatActivity() {
    private lateinit var recycleviewadpter: RecyclerView.Adapter<*>

    private lateinit var recycleviewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        var dataset = arrayOf<String>("データ1", "データ2", "データ3")

        recycleviewManager = LinearLayoutManager(this)
        recycleviewadpter = MyAdapter(dataset)

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = recycleviewManager
            adapter = recycleviewadpter
            itemAnimator = FadeInAnimator()
        }
    }

    class simpleitemanimator : SimpleItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("Not yet implemented")
        }

        override fun runPendingAnimations() {
            TODO("Not yet implemented")
        }

        override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
            TODO("Not yet implemented")
        }

        override fun animateChange(oldHolder: RecyclerView.ViewHolder?, newHolder: RecyclerView.ViewHolder?, fromLeft: Int, fromTop: Int, toLeft: Int, toTop: Int): Boolean {
            TODO("Not yet implemented")
        }

        override fun isRunning(): Boolean {
            TODO("Not yet implemented")
        }

        override fun endAnimation(item: RecyclerView.ViewHolder) {
            TODO("Not yet implemented")
        }

        override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("Not yet implemented")
        }

        override fun endAnimations() {
            TODO("Not yet implemented")
        }

    }

    class MyAdapter(private val myDataset: Array<String>) :
            RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView

            init {
                // Define click listener for the ViewHolder's View.
                textView = view.findViewById(R.id.recycletextview)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_recycleview, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.textView.text = myDataset[position]
            viewHolder.textView.setOnClickListener(
                    View.OnClickListener {
                        Timber.d("クリックしました")
                    }

            )
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }
}