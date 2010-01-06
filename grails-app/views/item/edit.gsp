<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Edit Item</title>
</head>
<body>
<div class="body" style="width: 600px">
    <h1>Edit Item</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${command.hasErrors()}">
        <div class="errors">
            <g:renderErrors bean="${command}" as="list"/>
        </div>
    </g:if>
    <g:form action="save" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${command.id}"/>
            <table>
                <tbody>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='product'>Product:</label></td>
                        <td valign='top' class='value'><g:select optionKey="id" from="${Product.list()}"
                                                                 name='product.id'
                                                                 optionValue="name"
                                                                 value="${command.product?.id}"></g:select></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='name'>Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='name' name='name'
                                                              value="${command.name}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='description'>Description:</label></td>
                        <td valign='top' class='value'>
                            <g:textArea name="description" value="${command.description}" escapeHtml="true"/>
                        </td>
                    </tr>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='tagString'>Tags:</label></td>
                        <td valign='top' class='value'><input type='text' id='tagString' name='tagString' value="${command.tagString}"/></td>
                    </tr>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='price'>Price:</label></td>
                        <td valign='top' class='value'><input type='text' id='price' name='price'
                                                              value="${command.price}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.street1'>Street1:</label></td>
                        <td valign='top' class='value'><input type="text" id='street1' name='address.street1'
                                                              value="${command.address.street1}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.street2'>Street2:</label></td>
                        <td valign='top' class='value'><input type="text" id='street2' name='address.street2'
                                                              value="${command.address.street2}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.city'>City:</label></td>
                        <td valign='top' class='value'><input type="text" id='city' name='address.city'
                                                              value="${command.address.city}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.state'>State:</label></td>
                        <td valign='top' class='value'><input type="text" id='state' name='address.state'
                                                              value="${command.address.state}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='address.zip'>Zip:</label></td>
                        <td valign='top' class='value'><input type="text" id='zip' name='address.zip'
                                                              value="${command.address.zip}"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.firstName'>First Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='firstName' name='contactInfo.firstName'
                                                              value="${command.contactInfo.firstName}"/>
                        </td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.lastName'>Last Name:</label></td>
                        <td valign='top' class='value'><input type="text" id='lastName' name='contactInfo.lastName'
                                                              value="${command.contactInfo.lastName}"/>
                        </td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='contactInfo.email'>Email:</label></td>
                        <td valign='top' class='value'><input type="text" id='email' name='contactInfo.email'
                                                              value="${command.contactInfo.email}"/></td>
                    </tr>

                    <g:if test="${command.imageUrl}">
                    <tr>
                        <td><input type="hidden" name="imageUrl" value="${command.imageUrl}"/></td>
                        <td><img src="${ps.thumbnailImage(imageUrl:command.imageUrl)}" alt="${command.imageUrl}"/></td>
                    </tr>
                    </g:if>

                    <tr class='prop'>
                        <td valign='top' class='name'><label for='file'>File:</label></td>
                        <td valign='top' class='value'><input type="file" id='file' name='file'/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'><label for='captcha'>Captcha:</label></td>
                        <td valign='top' class='value'><img src="${createLink(controller:"captcha")}" alt="captcha"/></td>
                    </tr>
                    <tr class='prop'>
                        <td valign='top' class='name'></td>
                        <td valign='top' class='value'><input type="text" id='captcha' name='captcha'/></td>
                    </tr>
                </tbody>
            </table>
        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" value="Update" action="save"/></span>
            <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" action="delete"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
