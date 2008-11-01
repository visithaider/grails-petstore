import org.apache.commons.validator.CreditCardValidator
import static org.apache.commons.validator.CreditCardValidator.*

class CreditCardCommand implements Serializable {

    String number
    Integer type, expirationYear, expirationMonth

    private static final def creditCardValidator = new CreditCardValidator(
        AMEX + VISA + MASTERCARD
    )

    static constraints = {
        type(inList:[AMEX, VISA, MASTERCARD])
        number(validator: { creditCardValidator.isValid it })
        expirationYear(min:2009, max:2020)
        expirationMonth(min:1, max:12)
    }

}
