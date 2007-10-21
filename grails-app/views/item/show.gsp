  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Item</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Item List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Item</g:link></span>
        </div>
        <div class="body">
            <h1>Show Item</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name" colspan="2"><h1>Pet</h1></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            <td valign="top" class="value">${item.product?.name}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Category:</td>
                            <td valign="top" class="value">${item.product?.category?.name}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            <td valign="top" class="value">${item.product?.description}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Price:</td>
                            <td valign="top" class="value">${item.price}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Image:</td>
                            <td valign="top" class="value"><img src="/grails-petstore/images/uploaded/${item.imageURL}" alt=""/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Tags:</td>
                            <td valign="top" class="value">${item.tagsAsString()}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Number Of Votes:</td>
                            <td valign="top" class="value">${item.numberOfVotes}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Total Score:</td>
                            <td valign="top" class="value">${item.totalScore}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name" colspan="2"><h1>Seller</h1></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Address:</td>
                            <td valign="top" class="value">${item.address?.encodeAsHTML()}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Contact Info:</td>
                            <td valign="top" class="value">${item?.contactInfo?.encodeAsHTML()}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="item">
                    <input type="hidden" name="id" value="${item?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
