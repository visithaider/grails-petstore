class CustomerOrderController {

    ShoppingCart shoppingCart

    def checkoutFlow = {

        createOrder {
            action {
                def order = new CustomerOrder(shoppingCart)
                flow.order = order
                [order:order]
            }

            on("success").to "showOrder"            
        }

        showOrder {
            on("forward").to "enterPersonalDetails"

            on("continueShopping").to "displayCatalog"
        }

        displayCatalog {
            redirect(controller:"item", action:"list")
        }

        enterPersonalDetails {
            on("forward") {
                def customer = flow.customer ?: new Customer(address:new Address())
                customer.properties = params["customer"]
                customer.address.properties = params["customer.address"]
                flow.customer = customer

                if (params.separateBillingAddress) {
                    def billingAddress = flow.order?.billingAddress ?: new Address()
                    billingAddress.properties = params["billingAddress"]
                    flow.order.billingAddress = billingAddress
                } else {
                    flow.order.billingAddress = null
                }

                if (!flow.customer.validate() ||
                    (flow.order.billingAddress &&
                    !flow.order.billingAddress.validate())) {
                    return error()
                }
            }.to "enterPaymentDetails"

            on("back").to "showOrder"
        }

        enterPaymentDetails {
            on("forward") {
                def creditCard = flow.creditCard
                if (!creditCard) {
                    creditCard = new CreditCardCommand()
                    flow.creditCard = creditCard
                }
                creditCard.properties = params

                if (!creditCard.validate()) {
                    return error()
                }
            }.to "reviewOrder"

            on("back").to "enterPersonalDetails"
        }

        reviewOrder {
            on("back").to "enterPaymentDetails"

            on("changeOrder").to "showOrder"
            
            on("changePersonalDetails").to "enterPersonalDetails"

            on("changePaymentDetails").to "enterPaymentDetails"

            on("confirm") {
                def order = flow.order
                def customer = flow.customer
                order.customer = customer
                if (order.save()) {
                    shoppingCart.contents.clear()
                } else {
                    return error()
                }
            }.to "thanks"
        }
        
        thanks()
    }

}
