package org.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.util.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientRest implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String i = (String) execution.getVariable("immatricule");
		Client client = Client.create();
		WebResource target = client.resource("http://localhost:18080/RestConge-0.0.1-SNAPSHOT/rest/solde/"+i);
		ClientResponse response = target.accept("application/json").get(ClientResponse.class);
		System.out.println(response);
		if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
		String output = response.getEntity(String.class);
		System.out.println(output);
		JSONObject obj = new JSONObject(output);
		String n = obj.getString("immatricule");
		String a = obj.getString("nom");
		String p = obj.getString("prenom");
		long s = obj.getLong("soldeCP");
		response.close();
		execution.setVariable("immatricule", n);
		execution.setVariable("nom", a);
		execution.setVariable("prenom", p);
		execution.setVariable("soldeCP", s);
		
	}

}
