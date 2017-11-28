package camera.hj.cameracontroller.rtsp;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import camera.hj.cameracontroller.rtsp.protocolAdapter.RtpDecoder;
import camera.hj.cameracontroller.rtsp.protocolAdapter.RtpEncoder;

/**
 * Created by koaloffice on 2017/11/25.
 */

public class TcpConnector {
    private NioSocketConnector connector;
    public TcpConnector(IoHandler ioHandler) {
        this.connector = new NioSocketConnector();
        this.connector.setConnectTimeoutMillis(5000L);
        this.connector.getSessionConfig().setReadBufferSize(8192);
        this.connector.getSessionConfig().setMaxReadBufferSize(65536);
        this.connector.getSessionConfig().setReceiveBufferSize(65536);
        this.connector.getSessionConfig().setSendBufferSize(65536);
        this.connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new RtpEncoder(), new RtpDecoder()));
        this.connector.getFilterChain().addLast("_io_c_write", new ExecutorFilter(8, new IoEventType[]{IoEventType.WRITE}));
        this.connector.setHandler(ioHandler);
    }


}
