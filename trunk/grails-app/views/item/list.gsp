<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Item List</title>
    </head>
    <body>
        <div class="body">
            <h1>Item List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="Id" />
                            <g:sortableColumn property="imageURL" title="Image" />
                            <g:sortableColumn property="product.name" title="Name" />
                            <g:sortableColumn property="product.description" title="Description" />
                            <g:sortableColumn property="product.category.name" title="Category" />
                            <g:sortableColumn property="tags" title="Tags" />
                            <g:sortableColumn property="price" title="Price" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemList}" status="i" var="item">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${item.id}">${item.id?.encodeAsHTML()}</g:link></td>
                            <td><img src="${createLinkTo(dir:'images/scaled', file:item.imageURL?.encodeAsHTML())}" alt="" /></td>
                            <td>${item.product?.name?.encodeAsHTML()}</td>
                            <td>${item.product?.description?.encodeAsHTML()}</td>
                            <td>${item.product?.category?.name?.encodeAsHTML()}</td>
                            <td>${item.tagsAsString().encodeAsHTML()}</td>
                            <td>$ ${item.price?.encodeAsHTML()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Item.count()}" />
            </div>
        </div>
    </body>
</html>
