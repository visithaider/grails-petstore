import org.apache.commons.validator.CreditCardValidator
import org.apache.commons.validator.CreditCardValidator.CreditCardType

class CreditCardCommand {

    String number
    CreditCardType type
    Integer expirationYear
    Integer expirationMonth

    static CreditCardValidator creditCardValidator = new CreditCardValidator(
        CreditCardValidator.AMEX +
        CreditCardValidator.MASTERCARD +
        CreditCardValidator.VISA
    )

    static constraints = {
        number(validator {
            type.matches(it)
        })
        expirationYear(min:2009, max:2020)
        expirationMonth(min:1, max:12)
    }

}
