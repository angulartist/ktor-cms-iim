package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.launch

fun Route.apiRoutes(appComponents: AppComponents) {
    get("articles") {
        appComponents.getArticleListPresenter(object : ArticleListPresenter.View {
            override fun displayArticleList(list: List<Article>) {
                launch {
                    val json = Gson().toJson(list)
                    call.respondText(json, ContentType.Application.Json)

                }
            }
        }).start()
    }

    get("article/{id}") {
        val id = call.parameters["id"]!!.toIntOrNull()

        if (id == null) {
            call.respond(HttpStatusCode.NotFound)
        } else {
            appComponents.getArticlePresenter(object : ArticlePresenter.View {
                override fun displayArticle(article: Article, comments: List<Comment>) {
                    launch {
                        call.respondText(Gson().toJson(article), ContentType.Application.Json)
                    }
                }

                override fun displayNotFound() {
                     launch {
                         call.respond(HttpStatusCode.NotFound)
                     }
                }

                override fun displayBadFormat() {
                    launch {
                        call.respond(FreeMarkerContent("errors/format.ftl", null, null))
                    }
                }
            }).start(id)
        }
    }
}