<#-- @ftlvariable name="" type="belabes.mohamed.cms.tpl.DetailsContext" -->
Details:
<p>${article.id}</p>
<p>${article.title}</p>
<p>${article.text}</p>

Comments:
<form method="post">
    <textarea type="text" name="text" placeholder="Your comment..."></textarea>
    <button type="submit">Post</button>
</form>
<p>Lists:</p>:
<ul>
    <#list comments as comment>
        <li>
            ${comment.id} / ${comment.text}
        </li>
    </#list>
</ul>