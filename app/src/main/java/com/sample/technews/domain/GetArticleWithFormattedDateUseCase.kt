package com.sample.technews.domain

import com.sample.technews.data.models.Article
import com.sample.technews.domain.model.ArticleInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetArticleWithFormattedDateUseCase @Inject constructor(
    private val formatDateUseCase: FormatDateUseCase
) {

    operator fun invoke(article: Article) = ArticleInfo(
        article.author,
        article.content,
        article.description,
        formatDateUseCase(article.publishedAt),
        article.source.name,
        article.title,
        article.urlToImage
    )

}