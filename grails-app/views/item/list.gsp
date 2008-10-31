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
                <h3>Browse</h3>
                <ul id="categoryList">
                    <g:each in="${Category.list()}" var="c">
                    <!-- TODO: Move to controller interceptor -->
                    <% def a = params.action, id = params.id?.toLong()
                       boolean active = (a == "byCategory" && c.id == id) ||
                                        (a == "byProduct" && c.products.any { it.id == id }) %>
                    <li class="${active ? "active" : "inactive"}">
                        <a href="${createLink(controller:"item", action:"byCategory", id:c.id)}" title="${c.name}">
                            ${c.name} (${Item.countByCategory(c)})
                        </a>
                        <% if (active) { %>
                            <ul id="productList">
                                <g:each in="${c.products}" var="p">
                                <li>
                                    <a href="${createLink(controller:"item", action:"byProduct",id:p.id)}" title="${p.name}" >
                                        ${p.name} (${Item.countByProduct(p)})
                                    </a>
                                </li>
                                </g:each>
                            </ul>
                        <% } %>
                    </li>
                    </g:each>
                </ul>
                <div id="tags">
                    <h3>Tags</h3>
                    <!-- TODO: tag cloud -->
                    <g:each in="${Tag.list()}" var="t">
                        <a href="${createLink(controller:"tag",action:t.tag)}">${t.tag}</a>
                    </g:each>
                </div>
            </div>
            <div id="items">
                <h1>${headline}</h1>
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <g:if test="${total > itemList.size()}">
                    <div class="paginateButtons paginateTop">
                    <g:paginate action="${a}" total="${total}" id="${id}" params="${params}"/>
                    </div>
                </g:if>
                <g:render template="/item/itemListTemplate" model="[itemList:itemList]"/>
                <g:if test="${total > itemList.size()}">
                    <div class="paginateButtons paginateBottom">
                        <g:paginate action="${a}" total="${total}" id="${id}" params="${params}"/>
                    </div>
                </g:if>
            </div>
        </div>
    </body>
</html>
