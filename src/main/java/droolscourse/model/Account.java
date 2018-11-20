package droolscourse.model;

public class Account {
	
	private long accountNo;
    private double balance;

    public Account(long accountNo, double balance) {
        super();
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public Account() {
        super();
        // TODO Auto-generated constructor stub
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", balance=" + balance + "]";
	}
    
    

    /*
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuffer buff = new StringBuffer();
        buff.append("-----Account-----)\n");
        buff.append("Account no " + this.accountNo + "\n");
        buff.append("Balance " + this.balance + "\n");
        buff.append("-----End Account-)");
        return buff.toString();
    }

    */
}