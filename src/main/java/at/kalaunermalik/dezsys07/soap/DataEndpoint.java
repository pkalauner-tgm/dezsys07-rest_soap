package at.kalaunermalik.dezsys07.soap;

import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.util.EntryConverter;
import dezsys07.kalaunermalik.at.GetDataRequest;
import dezsys07.kalaunermalik.at.GetDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class DataEndpoint {
    private static final String NAMESPACE_URI = "at.kalaunermalik.dezsys07";

    private DataRepository dataRepository;

    @Autowired
    public DataEndpoint(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDataRequest")
    @ResponsePayload
    public GetDataResponse getData(@RequestPayload GetDataRequest request) {
        GetDataResponse response = new GetDataResponse();

        //response.setData(dataRepository.findData(request.getTitle()));
        List<dezsys07.kalaunermalik.at.Entry> dataList = new ArrayList<>(dataRepository.findData(request.getTitle()).size());
        for(Entry entry: dataRepository.findData(request.getTitle())){
            dataList.add(EntryConverter.entryToEntry(entry));
        }

        response.getEntry().addAll(dataList);
//        response.getData().addAll(dataRepository.findData(request.getTitle()));

        return response;
    }
}