package hello;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
	
	private Client client;
	
	public static void createNode() {
		Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
		this.client = node.client();
	}

}



