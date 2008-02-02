<%@ page import="org.grails.petstore.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${createLinkTo(dir:"css/item",file:"list.css")}"/>
        <title>Item List</title>
    </head>
    <body>
        <div class="body">
            <div id="browser">
                <ul id="categoryList">
                    <g:each in="${Category.list()}" var="c">
                    <li>
                        <a href="${createLink(action:list, params:[category:c.id])}">
                            <img src="${createLinkTo(dir:"images/category",file:c.imageUrl)}" alt="${c.name}"/>
                        </a> 
                        <g:if test="${c.id?.toString() == params.category}">
                            <ul id="productList">
                            <g:each in="${c.products}" var="p">
                                <li>
                                    <a href="">
                                        <img src="${createLinkTo(dir:"images/product",file:p.imageUrl)}" alt="${p.name}"/>
                                    </a>
                                </li>
                            </g:each>
                            </ul>
                        </g:if>
                    </li>
                    </g:each>
                </ul>
            </div>
            <div id="items">
                <h1>Item List</h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <g:render template="itemListTemplate" model="[itemList:itemList]"/>
                <div class="paginateButtons" style="clear: both">
                    <g:paginate total="${itemList.size()}" />
                </div>
            </div>
        </div>
    </body>
</html>
