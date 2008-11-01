<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${createLinkTo(dir: "css/customerOrder", file: "enterPersonalDetails.css")}"/>
    <title>Checkout</title>
</head>
<body>
<div class="body">
    <g:if test="${customer?.hasErrors() || billingAdress?.hasErrors()}">
    <div class="errors">
        <g:renderErrors bean="${customer}" as="list" />
        <g:renderErrors bean="${billingAddress}" as="list" />
    </div>
    </g:if>
    <g:form action="checkout">
        <table>
            <caption>
                Personal details
            </caption>
            <tbody>
                <tr>
                    <td>
                        <label for="customer.name">Name</label>
                    </td>
                    <td>
                        <g:textField name="customer.name" value="${customer?.name}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.email">Email</label>
                    </td>
                    <td>
                        <g:textField name="customer.email" value="${customer?.email}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><h4>Shipping address</h4></td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.address.street1">Street</label>
                    </td>
                    <td>
                        <g:textField name="customer.address.street1" value="${customer?.address?.street1}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.address.street2"></label>
                    </td>
                    <td>
                        <g:textField name="customer.address.street2" value="${customer?.address?.street2}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.address.city">City</label>
                    </td>
                    <td>
                        <g:textField name="customer.address.city" value="${customer?.address?.city}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.address.zip">Zipcode</label>
                    </td>
                    <td>
                        <g:textField name="customer.address.zip" value="${customer?.address?.zip}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="customer.address.state">State</label>
                    </td>
                    <td>
                        <g:textField name="customer.address.state" value="${customer?.address?.state}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <g:checkBox name="separateBillingAddress"/>
                        Billing address is different from shipping address
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><h4>Billing address</h4></td>
                </tr>
                <tr>
                    <td>
                        <label for="billingAddress.street1">Street</label>
                    </td>
                    <td>
                        <g:textField name="billingAddress.street1" value="${billingAddress?.street1}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="billingAddress.street2"></label>
                    </td>
                    <td>
                        <g:textField name="billingAddress.street2" value="${billingAddress?.street2}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="billingAddress.city">City</label>
                    </td>
                    <td>
                        <g:textField name="billingAddress.city" value="${billingAddress?.city}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="billingAddress.zip">Zipcode</label>
                    </td>
                    <td>
                        <g:textField name="billingAddress.zip" value="${billingAddress?.zip}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="billingAddress.state">State</label>
                    </td>
                    <td>
                        <g:textField name="billingAddress.state" value="${billingAddress?.state}"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <g:submitButton name="back" value="Back to checkout"/>
        <g:submitButton name="forward" value="Enter payment details"/>
    </g:form>
</div>
</body>
</html>
