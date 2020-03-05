package com.firstkotlin.mauriciofeijo.firstkotlin.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firstkotlin.mauriciofeijo.firstkotlin.R
import com.firstkotlin.mauriciofeijo.firstkotlin.models.Repository
import com.firstkotlin.mauriciofeijo.firstkotlin.models.User
import kotlinx.android.synthetic.main.repositories_fragment.*
import kotlinx.android.synthetic.main.repository_item_view.view.*
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class RepositoriesFragment : Fragment() {
    private val REPOSITORIES_LIST = "RepositoriesList_Key"
    private val USER = "User_Key"

    companion object {
        fun newInstance(user: User, repositories: ArrayList<Repository>) = RepositoriesFragment().apply{
            arguments = Bundle(1).apply {
                putParcelableArrayList(REPOSITORIES_LIST, repositories)
                putParcelable(USER, user)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "Repositórios"

        arguments?.let {
            val user = arguments!!.get(USER) as User

            profile_image.setImageBitmap(getBitmapFromURL("https://avatars0.githubusercontent.com/u/11986141?v=4"))
            username_textView.text = user.login

            repositories_list_recyclerview.adapter = RepositoriesListAdapter(arguments!![REPOSITORIES_LIST] as List<Repository>, activity!!)
            val layoutManager = LinearLayoutManager(activity)
            repositories_list_recyclerview.layoutManager = layoutManager
        }
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            Log.e("src", src)
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            return myBitmap
        } catch (e: IOException) {
            Toast.makeText(activity, "Imagem não disponivel", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            return null
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repositories_fragment, container, false)
    }

    class RepositoriesListAdapter(private val repositories: List<Repository>,
                                  private val context: Context) : RecyclerView.Adapter<RepositoriesListAdapter.RepositoryViewHolder>() {
        override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
            val note = repositories[position]
            holder.bindView(note)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.repository_item_view, parent, false)
            return RepositoryViewHolder(view)
        }

        override fun getItemCount(): Int {
            return repositories.size
        }

        class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindView(repository: Repository) {
                itemView.repository_title.text = repository.name
                itemView.repository_description.text = repository.description
            }

        }

    }
}