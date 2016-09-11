package com.tk.baas.service;

import com.tk.baas.model.Address;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;

public interface AddressService {
	/**
	 * 添加地址，通过userId 和 address
	 * @param userId
	 * @param address
	 * @return
	 */
	public boolean addAddress(String userId, Address address);
	
	/**
	 * 删除地址通过：用户id，地址id
	 * @param userId
	 * @param addressId
	 * @return
	 */
	public boolean deleteAddress(String userId, String addressId);
	
	/**
	 * 更新地址（不更新默认地址）
	 * @param address
	 * @return
	 */
	public boolean updateAddress(Address address) ;
	/**
	 * 只更新默认地址
	 * @param userId
	 * @param addressId
	 * @return
	 */
	public boolean updateDefaultAddress(String userId, String addressId);
	/**
	 * 更新整个地址类
	 * @param address
	 * @return
	 */
	public boolean updateAddressAll(String userId, Address address);
	
	/**
	 * 获得地址列表（用户id，关键字，当前页和pagesize）
	 * @param userId
	 * @param key
	 * @param page
	 * @return
	 */
	public ResultPage getAddress(String userId, String key, Page page);
	
}
