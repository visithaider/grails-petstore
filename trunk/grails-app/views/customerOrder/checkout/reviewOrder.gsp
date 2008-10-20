<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${createLinkTo(dir: "css/customerOrder", file: "checkout.css")}"/>
    <title>Checkout</title>
</head>
<body>
<div class="body">
    <div id="order">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <table border="1">
            <caption>Checkout</caption>
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Product</td>
                    <td>Price</td>
                    <td>Count</td>
                    <td>Total price</td>
                </tr>
            </thead>
            <tbody>
                <g:each in="${order.lineItems}" var="li">
                    <tr>
                        <td>${li.item.name}</td>
                        <td>${li.item.product.name}</td>
                        <td>$ ${li.item.price}</td>
                        <td>${li.itemCount}</td>
                        <td>$ ${li.totalPrice}</td>
                    </tr>
                </g:each>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4">Total order amount:</td>
                    <td>$ ${order.totalPrice}</td>
                </tr>
            </tfoot>
        </table>
        <g:form action="checkout">
            <g:submitButton name="changeOrder" value="Change order"/>
        </g:form>
    </div>
    <div id="personalDetails">
        Name: ${customer.name} <br/>
        Email: ${customer.email} <br/>
        Shipping address: ${customer.address} <br/>
        <% if (order.billingAddress) {  %>
        Billing address: ${order.billingAddress} <br/>
        <% } %>
        <g:form action="checkout">
            <g:submitButton name="changePersonalDetails" value="Change personal details"/>
        </g:form>
    </div>
    <div id="paymentDetails">
        Payment: VISA **** **** **** 1111
        <g:form action="checkout">
            <g:submitButton name="changePaymentDetails" value="Change payment details"/>
        </g:form>
    </div>
    <g:form action="checkout">
        <g:submitButton name="back" value="Back to payment details"/>
        <g:submitButton name="confirm" value="Confirm order"/>
    </g:form>
</div>
</body>
</html>
