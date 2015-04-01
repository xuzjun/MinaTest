package per.x.minatest.serverhandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.InetSocketAddress;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

/**
 * The class that will accept and process clients in order to properly
 * track the memory usage.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MemoryMonitor {

    public static final int PORT = 18567;

    protected static final Dimension PANEL_SIZE = new Dimension(300, 200);

    private JFrame frame;

    private JTabbedPane tabbedPane;

    public MemoryMonitor() throws IOException {

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
        acceptor.setHandler(new MemoryMonitorHandler(this));

        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("logger", new LoggingFilter());

        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
        dcfg.setReuseAddress(true);

        frame = new JFrame("Memory monitor");
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Welcome", createWelcomePanel());
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocation(300, 300);
        frame.setVisible(true);

        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("UDPServer listening on port " + PORT);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(PANEL_SIZE);
        panel.add(new JLabel("Welcome to the Memory Monitor"));
        return panel;
    }

    public static void main(String[] args) throws IOException {
        new MemoryMonitor();
    }
}