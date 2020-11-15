package OMM.util;

import java.awt.Dimension;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.SinglePassFFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

public class VideoConverter {

	public DownloadInfo convertVideo(String input, String output, String format, int threads, int quality, int fps,
			Dimension resolution, float volume, boolean doublePass) {

		DownloadInfo downloadInfo = new DownloadInfo(input, output, format);
		System.out.println(volume);
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				System.out.println("Converting video");
				downloadInfo.setPercentage(0);
				downloadInfo.setEta(0);
				downloadInfo.setStatus(DownloadStatus.DOWNLOADING);
				downloadInfo.setNotify(true);
				try {
					FFmpeg ffmpeg = new FFmpeg("C:\\Users\\Michael\\git\\YoutubeDownloader\\deps\\ffmpeg.exe");
					FFprobe ffprobe = new FFprobe("C:\\Users\\Michael\\git\\YoutubeDownloader\\deps\\ffprobe.exe");

					FFmpegProbeResult in = ffprobe.probe(input);

					FFmpegBuilder builder = null;

					switch (format) {
					case "mp4":
						builder = new FFmpegBuilder().setInput(in).overrideOutputFiles(true).addOutput(output)
								.setVideoCodec("libx264").setFormat(format).setVideoResolution(1280, 528)
								.setVideoBitRate(3000).disableSubtitle().setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
								.done();
						break;
					case "avi":
						builder = new FFmpegBuilder().addExtraArgs("-threads", Integer.toString(threads)).setInput(in)
								.overrideOutputFiles(true).addOutput(output).setVideoCodec("mpeg4")
								.addExtraArgs("-tag:v", "DIVX")
								.addExtraArgs("-filter:a", "\"volume=" + Float.toString(volume) + "\"")
								.setFormat(format).setVideoFrameRate(fps, 1).setVideoBitRate(4854000 / quality)
								.setVideoResolution(resolution.width, resolution.height).disableSubtitle()
								.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL).done();
						break;

					default:
						break;

					}

					ProgressListener listener = new ProgressListener() {

						final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

						@Override
						public void progress(Progress progress) {
							double percentage = progress.out_time_ns / duration_ns;

							downloadInfo.setPercentage((int) (percentage * 100));
							downloadInfo.setOut("[" + (int) (percentage * 100) + "%] frame:" + progress.frame + " time:"
									+ FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS) + " ms fps:"
									+ progress.fps.doubleValue() + " speed:" + progress.speed + "x");
							downloadInfo.setNotify(true);
						}
					};
					
					FFmpegJob job;
					if (doublePass)
						job = createTwoPassJob(ffmpeg, builder, listener);
					else
						job = createJob(ffmpeg, builder, listener);
					
					job.run();
				} catch (IOException e) {
					e.printStackTrace();
					downloadInfo.setPercentage(0);
					downloadInfo.setStatus(DownloadStatus.FAILED);
					downloadInfo.setNotify(true);
				}
				System.out.println("Finished converting video");
				downloadInfo.setPercentage(100);
				downloadInfo.setStatus(DownloadStatus.COMPLETE);
				downloadInfo.setNotify(true);

			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

		return downloadInfo;
	}

	private FFmpegJob createTwoPassJob(FFmpeg ffmpeg, FFmpegBuilder builder, ProgressListener listener) {
		return new TwoPassFFmpegJob(ffmpeg, builder, listener);
	}

	private FFmpegJob createJob(FFmpeg ffmpeg, FFmpegBuilder builder, ProgressListener listener) {
		return new SinglePassFFmpegJob(ffmpeg, builder, listener);
	}

}
