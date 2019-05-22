<#-- @ftlvariable name="" type="belabes.mohamed.cms.tpl.DetailsContext" -->

<h1>Admin Dashboard:</h1>

Details:
<p>${article.id}</p>
<p>${article.title}</p>
<p>${article.text}</p>

<p>Lists:</p>:
<ul>
    <#list comments as comment>
        <li>
            ${comment.id} / ${comment.text}
            &mdash; <a href="comment/delete/${comment.id}">Delete</a>
        </li>
    </#list>
</ul>