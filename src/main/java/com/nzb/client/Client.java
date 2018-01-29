package com.nzb.client;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class Client {
	public static void main(String[] args) {
		
		ClientBootstrap bootstrap = new ClientBootstrap();
		
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("hiHandler", new HiHandler());
				return pipeline;
			}
		});
		
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
		Channel channel = connect.getChannel();
		
		System.out.println("client start");
		
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("please input");
			channel.write(scan.next());
		}
		
		
	}

}
