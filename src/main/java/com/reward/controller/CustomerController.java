package com.reward.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reward.exception.RewardException;
import com.reward.model.CustomerPurchase;
import com.reward.request.CustomerPurchaseRequest;
import com.reward.response.CustomerRewardResponse;
import com.reward.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<CustomerPurchase> createCustomerPurchase(
			@Valid @RequestBody CustomerPurchaseRequest customerPurchaseRequest) {
		return ResponseEntity.status(HttpStatus.CREATED.value())
				.body(customerService.createCustomerPurchase(customerPurchaseRequest));
	}

	@GetMapping("{customerId}")
	public ResponseEntity<CustomerRewardResponse> getCustomerRewardPoints(@PathVariable String customerId)
			throws RewardException {
		return ResponseEntity.status(HttpStatus.OK.value()).body(customerService.getCustomerRewardPoints(customerId));
	}

}
