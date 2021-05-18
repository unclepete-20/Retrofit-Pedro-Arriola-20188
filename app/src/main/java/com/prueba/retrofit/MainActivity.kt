package com.prueba.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.prueba.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private val articleList = mutableListOf<Articles>()

    private var place: String ="us"
    private var type: String = "business"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)

        intRecyclerView()
        searchNew(type,place)

        binding.btnUsa.setOnClickListener{
            place = "us"
            searchNew(type,place)
            showMessage("United States")
        }
        binding.btnMX.setOnClickListener{
            place = "mx"
            searchNew(type,place)
            showMessage("México")
        }
        binding.btnJapan.setOnClickListener {
            place = "jp"
            searchNew(type,place)
            showMessage("Japan")
        }
        binding.btnNZ.setOnClickListener {
            place = "nz"
            searchNew(type,place)
            showMessage("New Zealand")
        }

        binding.btnEntertainment.setOnClickListener{
            type = "entertainment"
            searchNew(type,place)
            showMessage("Entertainment")
        }
        binding.btnHealth.setOnClickListener{
            type = "health"
            searchNew(type,place)
            showMessage("Health")
        }
        binding.btnScience.setOnClickListener {
            type = "science"
            searchNew(type,place)
            showMessage("Science")
        }
        binding.btnTech.setOnClickListener {
            type = "technology"
            searchNew(type,place)
            showMessage("Technology")
        }

    }

    private fun intRecyclerView(){
        adapter = ArticleAdapter(articleList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }

    private fun searchNew(category:String, country:String){
        val api = Retrofit2()

        CoroutineScope(Dispatchers.IO).launch {
            val call = api.getService()?.getNewsByCategory(country,category,"b3581136b49449d58b418da91ec1554c")
            val news: NewsResponse? = call?.body()

            runOnUiThread{
                if(call!!.isSuccessful){
                    if(news?.status.equals("ok")){
                        val article = news?.articles ?: emptyList()
                        articleList.clear()
                        articleList.addAll(article)
                        adapter.notifyDataSetChanged()
                    }else{
                        showMessage("Error en webservice")
                    }

                }else{
                    showMessage("Error en retrofit")
                }
            }
        }
    }
    private fun showMessage(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchNew(query.toLowerCase(Locale.ROOT), query.toLowerCase(Locale.ROOT))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}