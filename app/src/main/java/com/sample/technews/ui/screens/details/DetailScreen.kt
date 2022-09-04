package com.sample.technews.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import coil.compose.rememberAsyncImagePainter
import com.sample.technews.R
import com.sample.technews.domain.model.ArticleInfo
import com.sample.technews.ui.utils.*

@Composable
fun DetailScreen(articleInfo: ArticleInfo) {

    val scrollState = rememberScrollState()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .padding(detailsColumnPadding)
                .scrollable(scrollState, Orientation.Vertical)
        ) {

            articleInfo.run {
                Image(
                    painter = rememberAsyncImagePainter(model = urlToImage),
                    contentDescription = stringResource(id = R.string.desc_article_image),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(detailsImageCornerSize))
                        .fillMaxWidth()
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = detailsTextTopPadding)
                )

                Text(
                    text = stringResource(R.string.author, author.toString()),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = detailsAuthorTopPadding)
                )

                Text(
                    text = stringResource(R.string.source, source.toString()),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = detailsTextTopPadding)
                )

                Text(
                    text = stringResource(R.string.publish_date, publishedAt.toString()),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = detailsTextTopPadding)
                )

                if (!content.isNullOrBlank() || !description.isNullOrBlank()) {

                    val details = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = detailsDescFontSize
                            )
                        ) {
                            append(stringResource(R.string.description))
                        }
                        append("\n")
                        append(description ?: content ?: stringResource(R.string.no_desc))
                    }

                    Text(
                        text = details,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = detailsDescTextTopPadding)
                    )
                }

            }


        }

    }


}