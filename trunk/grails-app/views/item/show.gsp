<%@ page import="org.grails.petstore.*" %>
  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${createLinkTo(dir:"css/item",file:"show.css")}"/>
        <title>Show Item</title>
    </head>
    <body>
        <div class="body">
            <div id="item">
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <h1>${item.name}</h1>
                <div id="itemImage">
                    <img src="/grails-petstore/images/item/large/${item.imageUrl}" alt="${item.imageUrl}"/>
                </div>
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>
                            <td valign="top" class="value">${item.product?.description}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Product:</td>
                            <td valign="top" class="value">${item.product.name}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Category:</td>
                            <td valign="top" class="value">${item.product?.category?.name}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Price:</td>
                            <td valign="top" class="value">$ ${item.price}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Tags:</td>
                            <td valign="top" class="value">
                                <g:each in="${item.tags}" var="t">
                                    <g:link controller="tag" action="list" id="${t.id}">${t.tag}</g:link>&nbsp;
                                </g:each>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Vote:</td>
                            <td valign="top" class="value">
                                <g:each in="[1,2,3,4,5]" var="rating">
                                    <g:link action="voteFor" id="${item.id}" params="[rating:rating]"><button>${rating}</button></g:link>
                                </g:each>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Number Of Votes:</td>
                            <td valign="top" class="value">
                                ${item.numberOfVotes}
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Score:</td>
                            <td valign="top" class="value">Total: ${item.totalScore}, Average: ${item.checkAverageRating()}</td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name" colspan="2"><h1>Contact Info</h1></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Seller:</td>
                            <td valign="top" class="value">${item?.contactInfo?.encodeAsHTML()}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Address:</td>
                            <td valign="top" class="value">${item.address?.encodeAsHTML()}</td>
                        </tr>
                    </tbody>
                </table>
                <div style="clear: both"/>
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
