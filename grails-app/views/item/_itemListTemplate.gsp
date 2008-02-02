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
