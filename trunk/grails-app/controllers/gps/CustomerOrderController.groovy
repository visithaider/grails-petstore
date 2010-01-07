package gps

class CustomerOrderController {

    def shoppingCart

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
                def customer = new Customer(params["customer"])
                customer.address = new Address(params["customer.address"])
                flow.customer = customer

                if (params.separateBillingAddress) {
                    def billingAddress = new Address(params["billingAddress"])
                    flow.order.billingAddress = billingAddress
                } else {
                    flow.order.billingAddress = null
                }

                if (!flow.customer.validate() ||
                    (flow.order.billingAddress && !flow.order.billingAddress.validate())) {
                    return error()
                }
            }.to "enterPaymentDetails"

            on("back").to "showOrder"
        }

        enterPaymentDetails {
            on("forward") { CreditCardCommand creditCard ->
                flow.creditCard = creditCard

                if (!flow.creditCard.validate()) {
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
                // TODO save customer, interact with payment system and probably send confirmation email
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
