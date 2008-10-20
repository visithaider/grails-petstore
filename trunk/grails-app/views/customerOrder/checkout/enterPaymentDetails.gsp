<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${createLinkTo(dir: "css/customerOrder", file: "checkout.css")}"/>
    <title>Checkout</title>
</head>
<body>
<div class="body">
    <g:form action="checkout">
        <table>
            <caption>Payment details</caption>
            <tbody>
                <tr>
                    <td colspan="2">
                        <g:radio name="card.type" value="VISA"/>
                        VISA
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <g:radio name="card.type" value="Mastercard"/>
                        Mastercard
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <g:radio name="card.type" value="American Express"/>
                        American Express
                    </td>
                </tr>
                <tr>
                    <td>Card number</td>
                    <td><g:textField name="card.number"/></td>
                </tr>
                <tr>
                    <td>Expires</td>
                    <td>
                        <g:select from="${2009..2020}" value="2009"/>
                        <g:select from="${1..12}" value="1"/>
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
