<%@ page import="org.grails.petstore.*" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Edit Item</title>
</head>
<body>
<div class="body">
    <h1>Edit Item</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <g:eachError bean="${item}">
        <g:message error="${it}" default=""/><br/>
    </g:eachError>

    <g:form action="save" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${item?.id}"/>

        <div class="dialog">
            <table>
                <tbody>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='product'>Product:</label></td>
                        <td valign='top' class='value'><g:select optionKey="id" from="${Product.list()}"
                                                                 name='product.id'
                                                                 optionValue="name"
                                                                 value="${item?.product?.id}"></g:select></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='name'>Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='name' name='name'
                                                              value="${item?.name?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='description'>Description:</label></td>
                        <td valign='top' class='value'>
                            <g:textArea name="description" value="${item?.description}" escapeHtml="true"/>
                        </td>
                    </tr>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='tagNames'>Tags:</label></td>
                        <td valign='top' class='value'><input type='text' id='tagNames' name='tagNames'
                                                              value="${item?.tagsAsString()}"/></td>
                    </tr>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='price'>Price:</label></td>
                        <td valign='top' class='value'><input type='text' id='price' name='price'
                                                              value="${item?.price}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.street1'>Street1:</label></td>
                        <td valign='top' class='value'><input type="text" id='street1' name='address.street1'
                                                              value="${item?.address?.street1?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.street2'>Street2:</label></td>
                        <td valign='top' class='value'><input type="text" id='street2' name='address.street2'
                                                              value="${item?.address?.street2?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.city'>City:</label></td>
                        <td valign='top' class='value'><input type="text" id='city' name='address.city'
                                                              value="${item?.address?.city?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.state'>State:</label></td>
                        <td valign='top' class='value'><input type="text" id='state' name='address.state'
                                                              value="${item?.address?.state?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.zip'>Zip:</label></td>
                        <td valign='top' class='value'><input type="text" id='zip' name='address.zip'
                                                              value="${item?.address?.zip?.encodeAsHTML()}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.firstName'>First Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='firstName' name='contactInfo.firstName'
                                                              value="${item?.contactInfo?.firstName?.encodeAsHTML()}"/>
                        </td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.lastName'>Last Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='lastName' name='contactInfo.lastName'
                                                              value="${item?.contactInfo?.lastName?.encodeAsHTML()}"/>
                        </td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.email'>Email:</label></td>
                        <td valign='top' class='value'><input type="text" id='email' name='contactInfo.email'
                                                              value="${item?.contactInfo?.email?.encodeAsHTML()}"/></td>
                    </tr>

                    <g:if test="${item?.imageUrl}">
                    <tr>
                    <td><input type="hidden" name="imageUrl" value="${item.imageUrl}"/></td>
                        <td><img src="${createLinkTo(dir:'images/item/thumbnail', file:item.imageUrl.encodeAsHTML())}" alt="item.imageUrl.encodeAsHTML()"/></td>
                    </tr>
                    </g:if>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='file'>File:</label></td>
                        <td valign='top' class='value'><input type="file" id='file' name='file'/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='captcha'>Captcha:</label></td>
                        <td valign='top' class='value'><img src="${createLinkTo(dir:'captcha')}" alt="Captcha"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'></td>
                        <td valign='top' class='value'><input type="text" id='captcha' name='captchaString'/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" value="Update"/></span>
            <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');"
                                                 value="Delete"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
