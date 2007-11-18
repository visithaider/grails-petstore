<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Tag List</title>
    </head>
    <body>
        <div class="body">
            <h1>Tag List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                   	        <g:sortableColumn property="tag" title="Tag" />
                            <th>Items tagged</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tagList}" status="i" var="tag">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${tag.id}">${tag.tag?.encodeAsHTML()}</g:link></td>
                            <td>${tag.items.size()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Tag.count()}" />
            </div>
        </div>
    </body>
</html>
