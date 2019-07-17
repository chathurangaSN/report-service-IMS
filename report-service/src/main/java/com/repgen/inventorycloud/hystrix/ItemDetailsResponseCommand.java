package com.repgen.inventorycloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.repgen.inventorycloud.modal.ItemDetailsResponse;
import com.repgen.inventorycloud.modal.ResponseMessages;
import com.repgen.inventorycloud.modal.StockMovementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ItemDetailsResponseCommand extends HystrixCommand<ItemDetailsResponse> {

    String itemCode;
    HttpHeaders httpHeaders;
    RestTemplate restTemplate;

    @Autowired
    ResponseMessages responseMessages;

    @Autowired
    ItemDetailsResponse itemDetailsResponse;

    public ItemDetailsResponseCommand( String itemCode, HttpHeaders httpHeaders, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("default")); // Integer brandId, Integer itemId, Integer uomId
        this.itemCode= itemCode;
        this.httpHeaders = httpHeaders;
        this.restTemplate = restTemplate;
    }
    @Override
    protected ItemDetailsResponse run() throws Exception {
        ResponseEntity<ItemDetailsResponse> responseEntity;
        HttpEntity< String> entity = new HttpEntity<>("",httpHeaders);
        responseEntity = restTemplate.exchange("http://"
                .concat(itemCode), HttpMethod.GET,entity,ItemDetailsResponse.class);
        System.out.println("succes got details"); // "/"+brandId+"/"+itemId+"/"+uomId
        return responseEntity.getBody();
    };

    @Override
    protected ItemDetailsResponse getFallback() {

        System.out.println("failed got details");
        itemDetailsResponse.setStatus(responseMessages.getResponseFailed());
        itemDetailsResponse.setMessage(responseMessages.getMessageFailedGET());
        itemDetailsResponse.setCode("#0000002");


        return itemDetailsResponse;
    }
}
