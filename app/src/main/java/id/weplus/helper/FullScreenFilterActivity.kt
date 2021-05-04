package id.weplus.helper

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import id.weplus.BaseActivity
import id.weplus.R
import kotlinx.android.synthetic.main.activity_full_screen_filter.*

class FullScreenFilterActivity : BaseActivity(), OnFilterClicked{
    var filterTexts = ArrayList<String>()
    private var resultCode=0;
    private lateinit var filterAdapter: FilterAdapter;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_filter)
        getData()
        setupRecyclerView()
        setupTextWatcher()
        setupCancelButton()
    }

    private fun setupCancelButton() {
        buttonCari.setOnClickListener {
            finish()
        }
    }

    private fun setupTextWatcher() {
        tvQuery.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               filterAdapter.filter.filter(p0.toString())
            }

        })
    }

    private fun getData(){
        filterTexts=intent.getStringArrayListExtra("searchList")!!
        resultCode = intent.getIntExtra("resultCode",0)
    }

    private fun setupRecyclerView(){
        filterAdapter = FilterAdapter(filterTexts,this)
        rvFilter.apply {
            layoutManager= LinearLayoutManager(this@FullScreenFilterActivity)
            adapter = filterAdapter
        }
    }

    override fun onFilterClick(text: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("result",text)
        setResult(resultCode,returnIntent)
        finish()
    }
}
