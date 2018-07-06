package tcc;

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
		
//		System.out.println("ID gerado: " + response.getId());
//		System.out.println("Indice gerado: " + response.getIndex());
//		System.out.println("Status: " + response.status().toString());
		
	}
	
	public void closeClient() {
		this.client.close();
		this.client = null;
	}
	
	public RespostaBusca pesquisaTermo(String termoPesquisa) {
		
		RespostaBusca retorno = new RespostaBusca();
		this.createClient();
		
		SearchResponse response = client.prepareSearch("documentos")
		        .setTypes("_doc")
		        .setQuery(QueryBuilders.queryStringQuery(termoPesquisa))                
		        .setSize(10)
		        .get();
		
		
		retorno.setTotalEncontrados(response.getHits().getTotalHits());
		
		response.getHits().forEach((element) -> {
			retorno.getResultado().add(new HitBusca(element.getId(), (String) element.getSourceAsMap().get("idMensagem"), element.getScore()));
		});
		
		this.closeClient();
		
		return retorno;
	}
	


}



