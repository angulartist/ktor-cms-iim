package belabes.mohamed.cms

import belabes.mohamed.cms.admin.AdminArticleListPresenter
import belabes.mohamed.cms.admin.AdminCallsPresenter
import belabes.mohamed.cms.control.ArticleListPresenterImpl
import belabes.mohamed.cms.control.ArticlePresenterImpl
import belabes.mohamed.cms.control.admin.AdminArticleListPresenterImpl
import belabes.mohamed.cms.control.admin.AdminCallsPresenterImpl

class AppComponents(mysqlUrl: String, mysqlUser: String, mysqlPassword: String) {
    private val pool = ConnectionPool(mysqlUrl, mysqlUser, mysqlPassword)

    private val model = MysqlModel(getPool())

    private fun getPool(): ConnectionPool = pool

    private fun getModel(): Model = model

    fun getArticleListPresenter(view: ArticleListPresenter.View): ArticleListPresenter = ArticleListPresenterImpl(getModel(), view)

    fun getArticlePresenter(view: ArticlePresenter.View): ArticlePresenter = ArticlePresenterImpl(getModel(), view)

    fun getAdminArticleListPresenter(view: AdminArticleListPresenter.View): AdminArticleListPresenter = AdminArticleListPresenterImpl(getModel(), view)

    fun getAdminCallsPresenter(view: AdminCallsPresenter.View): AdminCallsPresenter = AdminCallsPresenterImpl(getModel(), view)

}