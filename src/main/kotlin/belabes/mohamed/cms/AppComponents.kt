package belabes.mohamed.cms

import belabes.mohamed.cms.control.ArticleListPresenterImpl
import belabes.mohamed.cms.control.ArticlePresenterImpl

class AppComponents(mysqlUrl: String, mysqlUser: String, mysqlPassword: String) {
    private val pool = ConnectionPool(mysqlUrl, mysqlUser, mysqlPassword)

    private val model = MysqlModel(getPool())

    private fun getPool(): ConnectionPool = pool

    private fun getModel(): Model = model

    fun getArticleListPresenter(view: ArticleListPresenter.View): ArticleListPresenter = ArticleListPresenterImpl(getModel(), view)

    fun getArticlePresenter(view: ArticlePresenter.View): ArticlePresenter = ArticlePresenterImpl(getModel(), view)

}