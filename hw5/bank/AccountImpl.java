package cscie55.hw5.bank;

/**
 * @Author ssurabhi on 11/7/15.
 * @version 1.0
 */
public class AccountImpl implements Account {

    private int id;
    private long balance;

    /*constructor with id parameter */
    public AccountImpl(int id) {
        this.id = id;
    }

    /*
    Account Id.
    @param
    */
    public int id() {
        return id;
    }

    /*
   Return the balance.
   @param
   */
    @Override
    public long balance() {
        return balance;
    }

    /*
    deposit the amount.
    @param amount
   */
    @Override
    public void deposit(long amount){
        if(amount <= 0){
            throw new IllegalArgumentException("invalid deposit amount");
        }
        balance = balance+ amount;

    }

    /*
     withdraw the amount.
     @param amount
    */
    @Override
    public void withdraw(long amount) throws InsufficientFundsException {
        if(amount <= 0){
            throw new IllegalArgumentException("invalid withdrawal amount");
        }
        if(amount>balance){
            throw new InsufficientFundsException(this,amount);
        }
        balance = balance-amount;
    }
}
