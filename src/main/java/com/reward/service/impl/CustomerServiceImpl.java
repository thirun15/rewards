package com.reward.service.impl;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.reward.dao.CustomerPurchaseDao;
import com.reward.model.CustomerPurchase;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.response.CustomerRewardResponse;
import com.reward.response.RewardPerMonth;
import com.reward.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerPurchaseDao customerPurchaseDao;

	public CustomerServiceImpl(CustomerPurchaseDao customerPurchaseDao) {
		this.customerPurchaseDao = customerPurchaseDao;
	}

	@Override
	public CustomerPurchase createCustomerPurchase(@Valid CustomerPurchaseRequest customerPurchaseRequest) {
		CustomerPurchase customerPurchase = CustomerPurchase.builder().amount(customerPurchaseRequest.getAmount())
				.customerId(customerPurchaseRequest.getCustomerId())
				.purchaseDate(customerPurchaseRequest.getPurchaseDate())
				.reward(calculateRewards(customerPurchaseRequest.getAmount().intValue())).build();
		return customerPurchaseDao.save(customerPurchase);
	}

	private int calculateRewards(int amount) {
		int reward = 0;
		if (amount <= 50) {
			return reward;
		} else if (amount > 50 && amount <= 100) {
			reward += (amount - 50);
		} else if (amount > 100) {
			reward += 50;
			reward += (amount - 100) * 2;
		}
		return reward;
	}

	@Override
	public CustomerRewardResponse getCustomerRewardPoints(String customerId) {
		List<RewardPerMonth> rewardPerMonthList = new ArrayList<RewardPerMonth>();
		List<CustomerPurchase> customerPurchase = customerPurchaseDao.findByCustomerId(customerId);
		if (!customerPurchase.isEmpty()) {
			LocalDate date = LocalDate.now().minusMonths(3);
			customerPurchase = customerPurchase.stream()
					.filter(custoemr -> custoemr.getPurchaseDate().getMonth() >= date.getMonthValue())
					.collect(Collectors.toList());
			Map<Integer, List<CustomerPurchase>> purchaseOrderGroupBy = customerPurchase.stream()
					.collect(Collectors.groupingBy(rewards -> rewards.getPurchaseDate().getMonth()));

			for (Map.Entry<Integer, List<CustomerPurchase>> customerPurchaseGroup : purchaseOrderGroupBy.entrySet()) {
				RewardPerMonth rewardPerMonth = RewardPerMonth.builder()
						.monthName(new DateFormatSymbols().getMonths()[customerPurchaseGroup.getKey()])
						.totalRewards(
								customerPurchaseGroup.getValue().stream().mapToInt(CustomerPurchase::getReward).sum())
						.build();
				rewardPerMonthList.add(rewardPerMonth);
			}
		}
		return CustomerRewardResponse.builder().rewardPerMonth(rewardPerMonthList)
				.rewardTotal(customerPurchase.stream().mapToInt(CustomerPurchase::getReward).sum()).build();
	}

}
