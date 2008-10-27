<html>
    <head>
        <title><g:layoutTitle default="Grails Pet Store" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />				
    </head>
    <body>
	<div id="header">
        <div id="cart">
            <g:set var="sc" value="${applicationContext.shoppingCart}"/>
            <table>
                <caption>Shopping cart</caption>
                <tbody>
                    <g:each in="${sc.itemIds}" var="iid">
                        <tr>
                            <td>${sc.getItemCount(iid)}</td>
                            <td><g:link controller="item" action="show" id="${iid}">${Item.get(iid)?.name}</g:link></td>
                            <td>
                                <g:link controller="shoppingCart" action="add" id="${iid}">
                                    <img src="${createLinkTo(dir:"images",file:"add.png")}" alt="+"/>
                                </g:link>
                                <g:link controller="shoppingCart" action="remove" id="${iid}">
                                    <img src="${createLinkTo(dir:"images",file:"delete.png")}" alt="-"/>
                                </g:link>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <% if (sc.isEmpty()) { %>
                                <em>Empty</em>                            
                            <% } else { %>
                                <g:link controller="customerOrder" action="checkout">
                                    Checkout
                                </g:link>
                            <% } %>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <div class="logo"><img src="${createLinkTo(dir:'images',file:'gps_logo.png')}" alt="Grails" /></div>
        <div class="nav">
            <div id="searchableForm">
                <g:form url='[controller: "item", action: "search"]' name="searchableForm" method="get">
                    <g:textField name="q" value="${params.q}" size="20"/> <input type="submit" value="Search" />
                </g:form>
            </div>
            <div id="menuButtons">
                <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
                <span class="menuButton"><a class="list" href="${createLink(controller:"item",action:"list")}">Pet Catalog</a></span>
                <span class="menuButton"><a class="create" href="${createLink(controller:"item", action:"create")}">New Pet</a></span>
            </div>
            <div style="clear: both"> </div>
        </div>
	</div><!-- end header -->
        <g:layoutBody />
    </body>	
</html>
