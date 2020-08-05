package com.bazl.dna.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public class MatchThread {

	public ListenableFuture<Set<String>> process(ListeningExecutorService executorService, List<Object[]> list) {
		final ListenableFuture<Set<String>> future = executorService.submit(new Callable<Set<String>>() {
			@Override
			public Set<String> call() throws Exception {
				Set<String> result = Sets.newHashSet();
				for (Object[] o : list) {
					List<Object> l = Arrays.asList(o);
					result.add(l.size() + "");
				}
				return result;
			}
		});
		return future;
	}

	public void callback(ListeningExecutorService executorService, ListenableFuture<String> future) {
		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println(Thread.currentThread().getName() + ":" + result);
			}

			@Override
			public void onFailure(Throwable t) {
			}
		}, executorService);
	}

}
