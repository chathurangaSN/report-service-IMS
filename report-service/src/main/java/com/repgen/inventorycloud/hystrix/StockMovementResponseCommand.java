package com.repgen.inventorycloud.hystrix;



import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.repgen.inventorycloud.modal.StockMovementResponse;

public class StockMovementResponseCommand extends HystrixCommand<StockMovementResponse>{

//	Integer brandId;
	Integer itemId;
//	Integer uomId;
	HttpHeaders httpHeaders;
	RestTemplate restTemplate;
	
	public StockMovementResponseCommand( Integer itemId, HttpHeaders httpHeaders, RestTemplate restTemplate) {
		super(HystrixCommandGroupKey.Factory.asKey("default")); // Integer brandId, Integer itemId, Integer uomId
//		this.brandId= brandId;
		this.itemId= itemId;
//		this.uomId= uomId;
		this.httpHeaders = httpHeaders;
		this.restTemplate = restTemplate;
	}
	@Override
	protected StockMovementResponse run() throws Exception {
		ResponseEntity<StockMovementResponse>responseEntity;
		HttpEntity< String> entity = new HttpEntity<>("",httpHeaders);
		responseEntity = restTemplate.exchange("http://stock-service/stock/openstock/master/stockmovement/"
				.concat(String.valueOf("/"+itemId)),HttpMethod.GET,entity,StockMovementResponse.class);
		System.out.println("succes got details"); // "/"+brandId+"/"+itemId+"/"+uomId
		return responseEntity.getBody();
	};

	
}
