<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${createLinkTo(dir: "css/customerOrder", file: "checkout.css")}"/>
    <title>Checkout</title>
</head>
<body>
<div class="body">
    <g:if test="${creditCard?.hasErrors()}">
    <div class="errors">
        <g:renderErrors bean="${creditCard}" as="list" />
    </div>
    </g:if>
    <g:form action="checkout">
        <table>
            <caption>Payment details</caption>
            <tbody>
                <tr>
                    <td colspan="2">
                        <g:radio name="type" value="1" checked="${creditCard.type == 1}"/> American Express
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <g:radio name="type" value="2" checked="${creditCard.type == 2}"/> VISA
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <g:radio name="type" value="4" checked="${creditCard.type == 4}"/> Mastercard
                    </td>
                </tr>
                <tr>
                    <td>Card number</td>
                    <td><g:textField name="number" value="${creditCard.number}"/></td>
                </tr>
                <tr>
                    <td>Expires</td>
                    <td>
                        Year: <g:select from="${2009..2020}" name="expirationYear" value="${creditCard.expirationYear}"/>
                        Month: <g:select from="${1..12}" name="expirationMonth" value="${creditCard.expirationMonth}"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <g:submitButton name="back" value="Back to personal details"/>
        <g:submitButton name="forward" value="Review order"/>
    </g:form>
</div>
</body>
</html>
