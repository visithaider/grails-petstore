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
                <div class="list">
                    <table>
                        <thead>
                            <tr>
                                <th>Image</th>
                                <g:sortableColumn property="name" title="Name" />
                                <g:sortableColumn property="product.name" title="Product" />
                                <g:sortableColumn property="product.category.name" title="Category" />
                                <g:sortableColumn property="tags" title="Tags" />
                                <g:sortableColumn property="price" title="Price" />
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${itemList}" status="i" var="item">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td>
                                    <g:link action="show" id="${item.id}">
                                        <img src="${createLinkTo(dir:'images/item/thumbnail', file:item.imageUrl?.encodeAsHTML())}" alt="" />
                                    </g:link>
                                </td>
                                <td>
                                    <h2>
                                        <g:link action="show" id="${item.id}">${item.name?.encodeAsHTML()}</g:link>
                                    </h2>
                                    <p>
                                        ${item.description?.encodeAsHTML()}
                                    </p>
                                </td>
                                <td class="nowrap">${item.product?.name?.encodeAsHTML()}</td>
                                <td class="nowrap">${item.product?.category?.name?.encodeAsHTML()}</td>
                                <td>
                                    <g:each in="${item.tags}" var="tag">
                                        <a href="${createLink(controller:"tag", action:"list", id:tag.id)}">
                                            ${tag.tag.encodeAsHTML()}
                                        </a>
                                    </g:each>
                                </td>
                                <td>$${item.price?.encodeAsHTML()}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="paginateButtons" style="clear: both">
                    <g:paginate total="${itemList.size()}" />
                </div>
            </div>
        </div>
    </body>
</html>
