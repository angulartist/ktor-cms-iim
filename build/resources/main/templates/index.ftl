<#-- @ftlvariable name="" type="belabes.mohamed.cms.tpl.IndexContext" -->
<html>
    <head>
        <title>Blog</title>
        <link rel="stylesheet" href="./static/css/main.css">
    </head>
    <body>
    Index:
    <ul>
        <#list articles as article>
            <li>
                <a href="article/${article.id}">${article.title}</a>
            </li>
        </#list>
    </ul>
    </body>
</html>