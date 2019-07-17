package com.repgen.inventorycloud.hystrix;



import com.repgen.inventorycloud.modal.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.repgen.inventorycloud.modal.StockMovementResponse;

public class StockMovementResponseCommand extends HystrixCommand<StockMovementResponse>{

	String itemCode;
	HttpHeaders httpHeaders;
	RestTemplate restTemplate;

	@Autowired
	ResponseMessages responseMessages;

	@Autowired
	StockMovementResponse stockMovementResponse;
	
	public StockMovementResponseCommand( String itemCode, HttpHeaders httpHeaders, RestTemplate restTemplate) {
		super(HystrixCommandGroupKey.Factory.asKey("default")); // Integer brandId, Integer itemId, Integer uomId
		this.itemCode= itemCode;
		this.httpHeaders = httpHeaders;
		this.restTemplate = restTemplate;
	}
	@Override
	protected StockMovementResponse run() throws Exception {
		ResponseEntity<StockMovementResponse>responseEntity;
		HttpEntity< String> entity = new HttpEntity<>("",httpHeaders);
		responseEntity = restTemplate.exchange("http://stock-service/stock/openstock/master/stockmovement/"
				.concat(itemCode),HttpMethod.GET,entity,StockMovementResponse.class);
		System.out.println("succes got details"); // "/"+brandId+"/"+itemId+"/"+uomId
		return responseEntity.getBody();
	};

	@Override
	protected StockMovementResponse getFallback() {

		System.out.println("failed got details");
		stockMovementResponse.setStatus(responseMessages.getResponseFailed());
		stockMovementResponse.setMessage(responseMessages.getMessageFailedGET());
		stockMovementResponse.setCode("#0000002");
		stockMovementResponse.setStock(null);
		stockMovementResponse.setTransactionLogsIssue(null);
		stockMovementResponse.setTransactionLogsRecived(null);

		return stockMovementResponse;
	}
}
