package belabes.mohamed.cms.tpl

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

data class DetailsContext(val article: Article, val comments: List<Comment>)