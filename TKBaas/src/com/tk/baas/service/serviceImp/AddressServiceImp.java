package com.tk.baas.service.serviceImp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tk.baas.dao.AddressDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.service.AddressService;

@Component("AddressService")
public class AddressServiceImp implements AddressService {
	
	@Resource(name="AddressDao")
	private AddressDao addressDao;//调用dao
	
	@Override
	public boolean addAddress(String userId, Address address) {
		// TODO Auto-generated method stub
		return addressDao.addAddress(userId, address);
	}

	@Override
	public boolean deleteAddress(String userId, String addressId) {
		// TODO Auto-generated method stub
		return addressDao.deleteAddress(userId, addressId);
	}

	@Override
	public boolean updateAddress(Address address) {
		// TODO Auto-generated method stub
		return addressDao.updateAddress(address);
	}

	@Override
	public boolean updateDefaultAddress(String userId, String addressId) {
		// TODO Auto-generated method stub
		return addressDao.updateDefaultAddress(userId, addressId);
	}

	@Override
	public ResultPage getAddress(String userId, String key, Page page) {
		// TODO Auto-generated method stub
		return addressDao.getAddress(userId, key, page);
	}

	@Override
	public boolean updateAddressAll(String userId, Address address) {
		// TODO Auto-generated method stub
		return addressDao.updateAddressAll(userId, address);
	}


}
