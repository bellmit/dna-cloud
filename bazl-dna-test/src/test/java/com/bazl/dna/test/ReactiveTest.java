package com.bazl.dna.test;

import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Mono;

public class ReactiveTest {

	public static void main(String[] args) {
		ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
		WebClient client = WebClient.builder().clientConnector(httpConnector).build();
		
		// get
		String url = "http://192.168.1.199:8711/rapid/ystr/get/42";
		Mono<String> mono = client.get().uri(url).retrieve().bodyToMono(String.class);
		System.out.println(mono.block());
		
		// post
		String url1 = "http://192.168.1.199:8707/user/list";
		Mono<String> mono1 = client.post().uri(url1)
				.body(BodyInserters.fromValue(new JSONObject()))
				.retrieve().bodyToMono(String.class);
		System.out.println(mono1.block());
	}

}
