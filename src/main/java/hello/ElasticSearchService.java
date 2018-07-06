package hello;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {

	TransportClient client;
		
	public void createClient() {
		try {
			this.client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createDocument(String filename, String mensagemId, String texto) {
	
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("filename", filename);
		json.put("postDate",new Date());
		json.put("texto", texto);
		json.put("idMensagem", mensagemId);
		
		IndexResponse response = client.prepareIndex("documentos", "_doc")
		        .setSource(json, XContentType.JSON)
		        .get();
		
		System.out.println("ID gerado: " + response.getId());
		System.out.println("Indice gerado: " + response.getIndex());
//		System.out.println("Tipo gerado: " + response.getType());
		System.out.println("Status: " + response.status().toString());
		
	}
	
	public void closeClient() {
		this.client.close();
	}
	
	public Long pesquisaTermo(String termoPesquisa) {
		
		this.createClient();
		
		SearchResponse response = client.prepareSearch("documentos")
		        .setTypes("_doc")
		        .setQuery(QueryBuilders.queryStringQuery(termoPesquisa))                 // Query
		        .setSize(10)
		        .get();
		
		
		System.out.println(response.getHits().getTotalHits() + " documentos encontrados.");
		Long retorno = response.getHits().getTotalHits();
		
		List<HitBusca> retorno = new ArrayList<HitBusca>();
		
		response.getHits().forEach((element) -> {
//			retorno.add(new HitBusca(element.getId(), element.getFields().get("idMensagem").getValue(), (element.getScore()) ))
		});
		
		this.closeClient();
		return retorno;
	}
	


}



