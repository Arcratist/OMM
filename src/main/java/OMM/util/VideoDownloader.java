package OMM.util;

import com.sapher.youtubedl.DownloadProgressCallback;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;

public class VideoDownloader {

	public DownloadInfo downloadVideo(String url, String output, String format, String resolution) {
		System.out.println("Starting " + format + " Download Of " + url + " To " + output + "...");
		DownloadInfo downloadInfo = new DownloadInfo(url, output);
		downloadInfo.setPercentage(0);
		downloadInfo.setEta(0);
		downloadInfo.setStatus(DownloadStatus.DOWNLOADING);
		downloadInfo.setNotify(true);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					YoutubeDLResponse response = makeRequest(url, output, format, resolution, new DownloadProgressCallback() {

						@Override
						public void onProgressUpdate(float percentage, long eta) {
							System.out.println("Percentage: " + percentage + ", ETA: " + eta + "s");
							downloadInfo.setPercentage(percentage);
							downloadInfo.setEta(eta);
							downloadInfo.setNotify(true);
						}
					});
					downloadInfo.setOut(response.getOut());
				} catch (YoutubeDLException e) {
					e.printStackTrace();
					System.out.println(format + " Download Failed!");
					downloadInfo.setPercentage(0);
					downloadInfo.setEta(0);
					downloadInfo.setStatus(DownloadStatus.FAILED);
					downloadInfo.setNotify(true);
				}
				System.out.println("Download Complete!");
				downloadInfo.setPercentage(100);
				downloadInfo.setEta(0);
				downloadInfo.setStatus(DownloadStatus.COMPLETE);
				downloadInfo.setNotify(true);
			}
		};

		Thread thread = new Thread(runnable, "OMM_ytvideo_downloader");
		thread.start();
		
		return downloadInfo;
	}

	public YoutubeDLResponse makeRequest(String videoUrl, String directory, String format, String resolution, DownloadProgressCallback callback)
			throws YoutubeDLException {
		YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);

		request.setOption("ignore-errors"); // --ignore-errors
		request.setOption("restrict-filenames"); // --restrict-filenames
		request.setOption("output", "%(uploader)s/%(title)s.%(ext)s"); // --output <param>
		request.setOption("merge-output-format", format); // --merge-output-format <param>
		request.setOption("retries", 10); // --retries <param>
		request.setOption("format", "(\"bestvideo[height>=" + resolution + "]\"/bestvideo)+bestaudio/best"); // --format <param>

		if (callback == null)
			return YoutubeDL.execute(request);
		else
			return YoutubeDL.execute(request, callback);
	}
}
