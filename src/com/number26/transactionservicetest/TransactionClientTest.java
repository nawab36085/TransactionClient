package com.number26.transactionservicetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class TransactionClientTest {

	public static void main(String[] args){
		int size = args.length;
		String server;
		String transactionId;
		String amount;
		String type;
		String parent_id = null;
		if(size < 4){
			System.out.println("Insufficient number of arguments");
			return;
		}
		else{
			server = args[0];
			transactionId = args[1];
			amount = args[2];
			type = args[3];
		}
		if(size > 4){
			parent_id = args[4];
		}

		//String server = "http://localhost:8080";
		if(transactionId == null || transactionId.equals(""))
			System.out.println("Transaction id is not provided.");
		long traId = 0;
		if(transactionId != null && transactionId.equals("") == false){
			try{
				traId = Long.valueOf(transactionId);
			}
			catch(Exception NumberFormatException){
				System.out.println("The format of tansaction id provided is not valid.");
			}
		}
		else{
			System.out.println("Transaction id is not provided.");
		}
		double amt;
		if(amount != null && amount.equals("") == false){
			try{
				amt = Double.parseDouble(amount);
			}
			catch(Exception NumberFormatException){
				System.out.println("The format of amount provided is not valid.");
			}
		}
		else{
			System.out.println("Amount of the transaction has not been provided.");
		}
		if(type == null || type.equals("") == true){
			System.out.println("Type of the transaction has not been provided.");
		}
		long parentId = -1;
		if(parent_id != null && parent_id.equals("") == false){
			try{
				parentId = Long.parseLong(parent_id);
			}
			catch(Exception NumberFormatException){
				System.out.println("The format of parent id provided is not valid.");
			}
		}
		String url = server + "/TransactionManagement/transactionservice/transaction/"+ traId;
		System.out.println("so url is "+url);
		putTransactionClient(url, amount, type, parent_id);
	}
	public static String putTransactionClient(String url, String amount, String type, String parent_id) {
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    StringBuilder result = new StringBuilder();
	    try {
	        HttpPut putRequest = new HttpPut(url);
	        putRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
	        putRequest.addHeader("Accept", "text/html");
	        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	    	urlParameters.add(new BasicNameValuePair("amount", amount));
	    	urlParameters.add(new BasicNameValuePair("type", type));
			if(parent_id != null && parent_id.equals("") == false){
				urlParameters.add(new BasicNameValuePair("parent_id", parent_id));
			}

	    	putRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
	        HttpResponse response = httpClient.execute(putRequest);
	        if (response.getStatusLine().getStatusCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + response.getStatusLine().getStatusCode());
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (response.getEntity().getContent())));
	        String output;
	        while ((output = br.readLine()) != null) {
	            result.append(output);
	        }
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    System.out.println(result.toString());
	    return result.toString();
	}
}
