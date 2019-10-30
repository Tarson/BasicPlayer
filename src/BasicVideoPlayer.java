
import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;


public class BasicVideoPlayer {
    public final JFrame frame;

    public static String mrl;

    public static MediaPlayerFactory mpf;

    public static EmbeddedMediaPlayer MediaPlayer;

    public static CanvasVideoSurface videoSurface;

    public static Canvas canvas;



    public static void main(final String[] args) {

        new NativeDiscovery().discover();


        mrl = "udp://@:40002";


        SwingUtilities.invokeLater(new Runnable() {

            @Override

            public void run() {

                BasicVideoPlayer vp = new BasicVideoPlayer();

                vp.start(mrl);


            }
        });
    }

    public BasicVideoPlayer() {

        frame = new JFrame("My First Media Player");
        frame.setBounds(200,100, 540, 340);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(e);
                MediaPlayer.release();
                mpf.release();
                System.exit(0);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        canvas = new Canvas();


        String[] args = {
               "--video-filter",
                "rotate",
                "--rotate-angle",
                "270",
                "--demux=h264",
                "--clock-jitter=100",
                "--live-caching=100",
                "--network-caching=500",
        };


        mpf = new MediaPlayerFactory(args);
        videoSurface = mpf.newVideoSurface(canvas);

        MediaPlayer = mpf.newEmbeddedMediaPlayer();
        MediaPlayer.setVideoSurface(videoSurface);


        contentPane.add(canvas, BorderLayout.CENTER);
        frame.setContentPane(contentPane);

        frame.setVisible(true);
    }

    public void start(String mrl) {

        MediaPlayer.playMedia(mrl);
    }
}

