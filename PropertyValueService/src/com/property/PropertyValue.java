package com.property;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

@Path("/propertyValue")
public class PropertyValue {
	
	private static final String WEBSERVICE_URI = "http://webservice-takehome-1.spookle.xyz/property?property_id=";
	private static final String PROPERTY_NOT_FOUND ="Property not found";
	private static final String VIRGINIA="VA";
	

	/**
	 * getValuedPropertyOwner is a service to provide Highest valued property owner details in VA state.
	 *
	 */
	@Path("/valuedOwner")
	@GET
	public String getValuedPropertyOwner(@QueryParam(value = "homeids") List<String> homeids){
		String ownerName = null;
		List<PropertyDetails> virginiaProperties = new ArrayList<PropertyDetails>();
		try{
			ownerName = getPropertyDetails(homeids, ownerName, virginiaProperties);
			Collections.sort(virginiaProperties , new sortByPropertyValue());
			if(null!=virginiaProperties && virginiaProperties.size() >0){
				ownerName =virginiaProperties.get(0).getOwner();
			}else{
				ownerName=PROPERTY_NOT_FOUND;
			}
		}catch(Exception e){
		}
		return ownerName;
		
	}

	/**
	 * getPropertyDetails is used to get the property details of each home id.
	 *
	 */
	private String getPropertyDetails(List<String> homeids, String ownerName,
			List<PropertyDetails> virginiaProperties) {
		for(int i=0; i<homeids.size();i++){
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			URI serviceURI = UriBuilder.fromUri(WEBSERVICE_URI+homeids.get(i)).build();
			WebTarget webTarget = client.target(serviceURI);
			PropertyDetails details=null;
			try{
				 details = webTarget.request(MediaType.TEXT_PLAIN).get(PropertyDetails.class);	
			}catch(Exception e){
				ownerName=PROPERTY_NOT_FOUND;
				continue;
			}
			if(details.getAddress().getState().equalsIgnoreCase(VIRGINIA)){
				virginiaProperties.add(details);
			}			
		}
		return ownerName;
	}

}

class sortByPropertyValue implements Comparator<PropertyDetails>{

	@Override
	public int compare(PropertyDetails a, PropertyDetails b) {
		return b.getValue() - a.getValue();
	}
	
}
