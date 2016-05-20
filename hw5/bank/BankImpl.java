package cscie55.hw5.bank;


import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @Author ssurabhi on 11/7/15.
 * @version 1.0
 */

public class BankImpl implements Bank{

   private Hashtable<Integer, Account> accounts = new Hashtable<Integer, Account>();

    /*
   add Account
   @param account
   */
    @Override
    public void addAccount(Account account) throws DuplicateAccountException {
        if(accounts.containsKey(account.id())){
            throw new DuplicateAccountException(account.id());
        }
        accounts.put(account.id(), account);

    }

    /*
    transferLockingAccounts
    @param fromId,toId,amount
   */
    @Override
    public void transfer(int fromId, int toId, long amount) throws InsufficientFundsException {

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);

        Account firstAccountLocked = (fromAccount.id() < toAccount.id()) ? fromAccount:toAccount;
        Account secondAccountLocked = (fromAccount.id() < toAccount.id()) ? toAccount:fromAccount;

        synchronized(firstAccountLocked) {
            synchronized (secondAccountLocked) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            }
        }
    }

    /*
   totalBalances
   @param
   */
    @Override
    public long totalBalances() {
        long totalBalance = 0;
        Enumeration<Integer> keys = accounts.keys();
        while(keys.hasMoreElements()){
            totalBalance = totalBalance + ((Account)accounts.get(keys.nextElement())).balance();
        }

        return totalBalance;
    }

    @Override
   public void deposit(int accountId, long amount){

        Account toAccount = accounts.get(accountId);
        toAccount.deposit(amount);
    }

}
