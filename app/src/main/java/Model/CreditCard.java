package Model;

public class CreditCard {
    int creditCardId;
    String ccName;
    String ccNumber;
    String ccExpiry;
    int customerId;

    public CreditCard(int creditCardId, String ccName, String ccNumber,
                      String ccExpiry, int customerId) {
        this.creditCardId = creditCardId;
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.ccExpiry = ccExpiry;
        this.customerId = customerId;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiry() {
        return ccExpiry;
    }

    public void setCcExpiry(String ccExpiry) {
        this.ccExpiry = ccExpiry;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
