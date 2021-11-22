package com.example.nguyenanhlinh_mvvm_newsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nguyenanhlinh_mvvm_newsapp.R
import com.example.nguyenanhlinh_mvvm_newsapp.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differList = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differList.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differList.currentList[position]
        holder.itemView.apply {
            if (article.urlToImage != null) {
                Picasso.with(context).load(article.urlToImage).into(ivArticleImage)
            } else {
                ivArticleImage.setImageResource(R.drawable.breaking_news)
            }

            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            @SuppressLint("SimpleDateFormat")
            val simpleDateFormat = SimpleDateFormat("EEE MMM dd hh:mm aaa", Locale.ENGLISH)
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            tvPublishedAt.text = simpleDateFormat.format(formatter.parse(article.publishedAt))

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}













