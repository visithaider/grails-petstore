<%@ page import="org.grails.petstore.*" %>
  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${createLinkTo(dir:"css/item",file:"show.css")}"/>
        <title>Show Item</title>
        <script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAAU5ND2jLlzfA0lYB9KhO2exQFxCRMmJ_u0ujLxBgyStU7cbIWpBQwU-AiRAKk50BIUA0-lRbdBQVehw"></script>
        <script type="text/javascript">
            //<![CDATA[
            function loadMap() {
              if (GBrowserIsCompatible()) {
                var map = new GMap2(document.getElementById("map"));
                var point = new GLatLng(${item.latitude}, ${item.longitude});
                map.setCenter(point, 10);
                map.addOverlay(new GMarker(point));
              }
            }
            //]]>
        </script>
    </head>
    <body onunload="GUnload()">
        <div class="body">
            <div id="item">
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <h1>${item.name}</h1>
                
                <div id="pet">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">Description:</td>
                                <td valign="top" class="value">${item.description}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">Product:</td>
                                <td valign="top" class="value">
                                    <a href="${createLink(controller:"item",action:"byProduct",id:item.product?.id)}">
                                    ${item.product?.name.encodeAsHTML()}
                                    </a>
                                    - ${item.product.description}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">Category:</td>
                                <td valign="top" class="value">
                                    <a href="${createLink(controller:"item",action:"byCategory",id:item.product?.category.id)}">
                                    ${item.product?.category.name.encodeAsHTML()}
                                    </a>
                                    - ${item.product.category.description}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">Price:</td>
                                <td valign="top" class="value">$ ${item.price}</td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">Tags:</td>
                                <td valign="top" class="value">
                                    <g:each in="${item.tags}" var="t">
                                        <g:link controller="tag" action="${t.tag}">${t.tag}</g:link>&nbsp;
                                    </g:each>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">Vote:</td>
                                <td valign="top" class="value">
                                    <g:each in="[1,2,3,4,5]" var="rating">
                                        <span class="rating">
                                            <a href="${createLink(action:"voteFor", id:item.id, params:[rating:rating])}">${rating}</a>
                                        </span>
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
                                <td valign="top" class="name">Average score:</td>
                                <td valign="top" class="value"><g:formatNumber number="${item.checkAverageRating()}" format="###.#"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <div id="itemImage">
                        <img src="${createLinkTo(dir:"images/item/large",file:item.imageUrl)}" alt="${item.imageUrl}"/>
                    </div>
                    <div style="clear: both"></div>
                </div>

                <h1>Contact Info</h1>
                <div id="contactInfo">
                    <table>
                        <tbody>
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
                    <div id="map">Google Map (uncomment javascript to enable)</div>
                    <div style="clear: both"></div>
                </div>

            </div>
            <div class="buttons">
                <g:form controller="item">
                    <input type="hidden" name="id" value="${item?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
        <script type="text/javascript">
            google.load("maps", "2");
            google.setOnLoadCallback(loadMap);
        </script>
    </body>
</html>
