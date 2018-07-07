package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{
	private Map<String, Customer> data = new HashMap<String, Customer>();
	/*public WalletServiceImpl(Map<String, Customer> data)
	{
		repo= new WalletRepoImpl(data);
	}*/
/*	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}*/
	WalletRepo servicedao;
	{
		servicedao=new WalletRepoImpl();
	}

	@Override
	public Customer createAccount(String name, String mobileno,
			BigDecimal amount) {

		if(validatemobile(mobileno))
		{
		Customer cu = new Customer(name, mobileno, new Wallet((amount)));
		servicedao.save(cu);
		data.put(mobileno, cu);
		return cu;
		}
		else
		{
		System.out.println("Invalid number");
		return null;
		}
		
	}
	public boolean validatemobile(String str) {
		String patterns = "[1-9][0-9]{9}";
		if (str.matches(patterns)) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Customer showBalance(String mobileno) {
		Customer customer=servicedao.findOne(mobileno);
		if(customer!=null)
			return customer;
	else	
			throw new InvalidInputException("Invalid mobile no ");
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo,
			BigDecimal amount) {
		Customer cu1=data.get(sourceMobileNo);
		Customer cu2=data.get(targetMobileNo);
		BigDecimal bg1=amount;
		Wallet w=cu1.getWallet();
		Wallet w1=cu2.getWallet();
		BigDecimal balance=w.getBalance();
		BigDecimal balance1=w1.getBalance();
		cu1.setWallet(new Wallet(balance.subtract(amount)));
		cu2.setWallet(new Wallet(balance1.add(amount)));
		return cu1;
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount1) {
		Customer cu1=data.get(mobileNo);
		/*MathContext pr=new MathContext(2);*/
		BigDecimal bg1=amount1;
		Wallet w=cu1.getWallet();
		BigDecimal balance=w.getBalance();
		BigDecimal deposit=balance.add(amount1);	
 cu1.setWallet(new Wallet(deposit));
		return cu1;
	}

	@Override
	public Customer withdrawAmount(String mobileNo1, BigDecimal amount2) {
Customer cu2=data.get(mobileNo1);
		BigDecimal bg1=amount2;
		BigDecimal bg2=new BigDecimal (0);
		Wallet w=cu2.getWallet();
		BigDecimal balance=w.getBalance();
		BigDecimal withdraw=balance.subtract(amount2);
		if(withdraw.compareTo(bg2)>0){

 cu2.setWallet(new Wallet(withdraw));
		return cu2;
		}
		else {
			System.out.println("Insufficient Balance");
			return null;
		}
	}
}
