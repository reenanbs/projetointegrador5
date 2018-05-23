package android.biker.com.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_historic.*
import java.util.*

/**
 * Created by VINTTAGE on 26/03/2018.
 */

class HistoricActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic)

        rvHistoricos.setHasFixedSize(true)
        rvHistoricos.layoutManager = LinearLayoutManager(this)

        val historicos:ArrayList<Historic> = ArrayList<Historic>()

        for(i in 0..9){
            historicos.add(Historic("2${i}/03/2018",
                    "${i}",
                    "${i}hr", "${i+1}hr"))
        }

        val mAdapter: RecyclerView.Adapter<*> = MyAdapter(this@HistoricActivity, historicos){
            Toast.makeText(this@HistoricActivity, it.toString(), Toast.LENGTH_SHORT).show()
        }
        rvHistoricos.adapter = mAdapter

    }
}
