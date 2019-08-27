package hello;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.GetNameRequest;
import io.spring.guides.gs_producing_web_service.GetNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        System.out.println(request.getName());
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNameRequest")
    @ResponsePayload
    public GetNameResponse getName(@RequestPayload GetNameRequest request) {
        GetNameResponse response = new GetNameResponse();
        response.setName("==========receive msg========"+request.getName());
        System.out.println(request.getName());
        return response;
    }
}