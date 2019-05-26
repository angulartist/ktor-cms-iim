package belabes.mohamed.cms.presenters

import belabes.mohamed.cms.model.Article

interface ArticleListPresenter {
    fun start()

    interface View {
        fun displayArticleList(list: List<Article>)
    }
}