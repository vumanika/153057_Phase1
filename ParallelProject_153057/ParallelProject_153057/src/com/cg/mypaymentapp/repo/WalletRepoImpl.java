package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;

public class WalletRepoImpl implements WalletRepo {
	private Map<String, Customer> data=new HashMap<String, Customer>(); 
	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}

	public WalletRepoImpl() {
		// TODO Auto-generated constructor stub
	}

	public boolean save(Customer customer) {
		data.put(customer.getMobileNo(), customer);
		return true;
	}

	public Customer findOne(String mobileNo) {
		Customer cu=data.get(mobileNo);
		return cu;
		
		
	}
}
