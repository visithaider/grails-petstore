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
                        <a href="${createLink(controller:"item", action:"byCategory", id:c.id)}" title="${c.name}">
                            <img src="${ps.categoryImage(category:c)}" alt="${c.name}"/>
                        </a>
                        <%  def a = params.action, id = params.id?.toLong()
                            if ((a == "byCategory" && c.id == id) ||
                                (a == "byProduct"  && c.products.any { it.id == id })) { %>
                            <ul id="productList">
                            <g:each in="${c.products}" var="p">
                                <li>
                                    <a href="${createLink(controller:"item", action:"byProduct",id:p.id)}" title="${p.name}" >
                                        <img src="${ps.productImage(product:p)}" alt="${p.name}"/>
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
                <h1>${headline}</h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <div class="paginateButtons paginateTop">
                    <g:paginate action="${a}" total="${total}" id="${id}" params="${params}"/>
                </div>
                <g:render template="/item/itemListTemplate" model="[itemList:itemList]"/>
                <div class="paginateButtons paginateBottom">
                    <g:paginate action="${a}" total="${total}" id="${id}" params="${params}"/>
                </div>
            </div>
        </div>
    </body>
</html>