package net.galvin.orange.core.transport.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.galvin.orange.core.Utils.SysEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyClientInboundHandler.class);
    private ChannelHandlerContext channelHandlerContext = null;

    private String resultVal;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("NettyClientInboundHandler ===>> channelActive");
        this.channelHandlerContext = ctx;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("NettyClientInboundHandler ===>> handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("NettyClientInboundHandler ===>> handlerRemoved");
        this.channelHandlerContext = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("NettyClientInboundHandler ===>> channelRead");
        ByteBuf byteBufMsg = (ByteBuf) msg;
        StringBuffer msgBuffer = new StringBuffer();
        while(byteBufMsg.isReadable()){
            msgBuffer.append((char) byteBufMsg.readByte());
        }
        this.resultVal = msgBuffer.toString();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("NettyClientInboundHandler ===>> exceptionCaught");
        logger.error(SysEnum.format(cause));
        this.channelHandlerContext = null;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public String getResultVal() {
        while(this.resultVal == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String tempVal = this.resultVal;
        this.resultVal = null;
        return tempVal;
    }
}