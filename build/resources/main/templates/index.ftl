<#-- @ftlvariable name="" type="belabes.mohamed.cms.tpl.IndexContext" -->
<html>
<head>
    <title>Blog</title>
    <link rel="stylesheet" href="./static/css/90s.css">
    <link rel="stylesheet" href="./static/css/main.css">
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container" style="width: auto;">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="/">The Beam Blog</a>
            <div class="nav-collapse">
                <ul class="nav">
                    <li class="active"><a href="/">Articles</a></li>
                </ul>
                <ul class="nav pull-right">
                    <li><a href="admin/">Admin Dashboard</a></li>
                </ul>
            </div><!-- /.nav-collapse -->
        </div>
    </div><!-- /navbar-inner -->
</div>

<div class="container">
    <div class="row">
        <div class="span12">
            <blink>
                <marquee>
                    Welcome to the Beam blog. Learn the most important streaming systems notions such as windows, triggers, the
                    watermark, parallel transforms and so on!
                </marquee>
            </blink>
            <center>
                <!-- TRIPLE MC HAMMER -->
                <img src="http://code.divshot.com/geo-bootstrap/img/test/mchammer.gif">&nbsp;&nbsp;
                <img src="http://code.divshot.com/geo-bootstrap/img/test/mchammer.gif">&nbsp;&nbsp;
                <img src="http://code.divshot.com/geo-bootstrap/img/test/mchammer.gif">
            </center>
            <div class="page-header">
                <h1>Latest articles <img src="http://code.divshot.com/geo-bootstrap/img/test/new2.gif"></h1>
            </div>
        </div>
    </div>

    <ul>
        <#list articles as article>
            <li>
                <a href="article/${article.id}">${article.title}</a>
            </li>
        </#list>
    </ul>
</div>
</body>
</html>