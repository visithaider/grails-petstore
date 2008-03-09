<div class="list">
    <table>
        <thead>
            <tr>
                <th>Image</th>
                <g:sortableColumn property="name" title="Name" action="${action}"/>
                <th>Product</th>
                <th>Category</th>
                <th>Tags</th>
                <g:sortableColumn property="price" title="Price" action="${action}"/>
            </tr>
        </thead>
        <tbody>
        <g:each in="${itemList}" status="i" var="item">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>
                    <g:link controller="item" action="show" id="${item.id}">
                        <img src="${ps.thumbnailImage(item:item)}" alt="" />
                    </g:link>
                </td>
                <td>
                    <h2>
                        <g:link controller="item" action="show" id="${item.id}">${item.name?.encodeAsHTML()}</g:link>
                    </h2>
                    <p>
                        ${item.description?.encodeAsHTML()}
                    </p>
                </td>
                <td class="nowrap">
                    <a href="${createLink(controller:"item",action:"byProduct",id:item.product?.id)}">
                        ${item.product?.name.encodeAsHTML()}
                    </a>
                </td>
                <td class="nowrap">
                    <a href="${createLink(controller:"item",action:"byCategory",id:item.product?.category.id)}">
                        ${item.product?.category.name.encodeAsHTML()}
                    </a>
                </td>
                <td>
                    <g:each in="${item.tags}" var="tag">
                        <a href="${createLink(controller:"tag", action:tag.tag)}">
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
