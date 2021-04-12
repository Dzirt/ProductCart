package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    private String msg;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Message: " + msg);
        ChatClient client = applicationContext.getBean("chatClient", ChatClient.class);
        client.updateChat(msg);
        setMsg(msg);
    }
    private void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
