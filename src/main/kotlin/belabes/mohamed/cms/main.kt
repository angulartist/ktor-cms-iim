package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import belabes.mohamed.cms.tpl.DetailsContext
import belabes.mohamed.cms.tpl.IndexContext
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.launch

class App

fun main() {
    val component = AppComponents("jdbc:mysql://localhost:8889/CMS?serverTimezone=UTC", "root", "root")

    val server = embeddedServer(Netty, port = 8080) {

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        routing {
            static ("static") {
                resources("static")
            }


            get("/article/{id}") {
                val id = call.parameters["id"]!!.toIntOrNull()

                var controller = component.getArticlePresenter(object : ArticlePresenter.View {
                    override fun displayArticle(article: Article, comments: List<Comment>) {
                        val context = DetailsContext(article, comments)
                        launch {
                            call.respond(FreeMarkerContent("details.ftl", context, null))
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
                })

                if (id  == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    controller.start(id)
                }
            }

            post("/article/{id}") {
                val id = call.parameters["id"]!!.toIntOrNull()
                val postParameters: Parameters = call.receiveParameters()

                val text: String? = postParameters["text"]

                var controller = component.getArticlePresenter(object : ArticlePresenter.View {
                    override fun displayArticle(article: Article, comments: List<Comment>) {
                        val context = DetailsContext(article, comments)
                        launch {
                            call.respond(FreeMarkerContent("details.ftl", context, null))
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
                })

                if (id == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    controller.postComment(id, text)
                }
            }

            get ("/") {
                var controller = component.getArticleListPresenter(object : ArticleListPresenter.View {
                    override fun displayArticleList(list: List<Article>) {
                        val context = IndexContext(list)
                        launch {
                            call.respond(FreeMarkerContent("index.ftl", context, null))
                        }
                    }
                })

                controller.start()
            }
        }
    }

    server.start(wait = true)
}