package OMM.util;

import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;

public class TorrentDownloader {

	private Thread thread;
	private Runnable runnable;

	public void downloadTorrent(String magnet, TorrentConfig torrentConfig) {

		runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Starting torrent download...");
				Config config = new Config() {
					@Override
					public int getNumOfHashingThreads() {
						return Runtime.getRuntime().availableProcessors() * 2;
					}
				};
				DHTModule dhtModule = new DHTModule(new DHTConfig() {
					@Override
					public boolean shouldUseRouterBootstrap() {
						return true;
					}
				});

				Path targetDirectory = Paths.get(torrentConfig.output_dir);
				Storage storage = new FileSystemStorage(targetDirectory);
				BtClient client = Bt.client().config(config).storage(storage).magnet(magnet).autoLoadModules()
						.module(dhtModule).stopWhenDownloaded().build();

				client.startAsync().join();
				System.out.println("Finished Torrent Download...");
			}
		};

		thread = new Thread(runnable, "OMM_torrent_downloader");
		thread.start();
	}

}
