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
                        <a href="${createLink(action:"byCategory", id:c.id)}">
                            <img src="${createLinkTo(dir:"images/category",file:c.imageUrl)}" alt="${c.name}"/>
                        </a>
                        <% if (c.id == params.category?.toLong() ||
                               c.products.any { it.id == params.product?.toLong()} ) { %>
                            <ul id="productList">
                            <g:each in="${c.products}" var="p">
                                <li>
                                    <a href="${createLink(action:"byProduct",id:p.id)}"
                                       title="${p.name}" >
                                        <img src="${createLinkTo(dir:"images/product",file:p.imageUrl)}" alt="${p.name}"/>
                                    </a>
                                </li>
                            </g:each>
                            </ul>
                        <% } %>
                    </li>
                    </g:each>
                </ul>
            </div>
            <div id="items">
                <h1>Showing ${total} pets</h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <div class="paginateButtons paginateTop">
                    <g:paginate total="${total}" id="${params.id}"/>
                </div>
                <g:render template="itemListTemplate" model="[itemList:itemList]"/>
                <div class="paginateButtons paginateBottom">
                    <g:paginate total="${total}" id="${params.id}"/>
                </div>
            </div>
        </div>
    </body>
</html>
