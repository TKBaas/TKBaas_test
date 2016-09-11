package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Address;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;

public interface AddressDao {
	
	public boolean addAddress(String userId ,Address address);
	
	public boolean deleteAddress(String userId, String addressId);
	
	public boolean updateAddress(Address address);
	public boolean updateDefaultAddress(String userId, String addressId);
	public boolean updateAddressAll(String userId, Address address);
	
	public ResultPage getAddress(String userId, String key, Page page);
}
