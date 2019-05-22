<#-- @ftlvariable name="" type="belabes.mohamed.cms.tpl.IndexContext" -->
<html>
    <head>
        <title>Dashboard | Blog</title>
        <link rel="stylesheet" href="./static/css/main.css">
    </head>
    <body>
    <h1>Admin Dashboard:</h1>
    <form action="article/add" method="post">
        <label>New article:</label>
        <input type="text" name="title" placeholder="Title">
        <textarea type="text" name="text" placeholder="Text"></textarea>
        <button>Add article</button>
    </form>
    <ul>
        <#list articles as article>
            <li>
                <a href="article/${article.id}">${article.title}</a>
                &mdash; <a href="article/delete/${article.id}">Delete article</a>
            </li>
        </#list>
    </ul>
    </body>
</html>