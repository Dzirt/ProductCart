package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

@Component
public class ChatClient {
    private Channel channel;
    private String host;
    private int port;
    private static JTextArea chat = new JTextArea();

    private int WIDTH;
    private int HEIGHT;

    public ChatClient(String host, int port) throws Exception{
        this.host = host;
        this.port = port;
    }

    public void runClient() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);


        chat.setEditable(false);
        chat.setBackground(Color.LIGHT_GRAY);
        chat.setAutoscrolls(true);
        JScrollPane chatScroll = new JScrollPane(chat);
        chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTextArea msgField = new JTextArea();
        msgField.setSize(WIDTH - 100, 30);
        msgField.setLineWrap(true);
        JScrollPane msgScroll = new JScrollPane(chat);
        msgScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        msgField.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setBackground(Color.GRAY);

        JButton authButton = new JButton("Send");
        authButton.addActionListener(e -> {
            try {
                channel.writeAndFlush(msgField.getText() + "\r\n");
                chat.append("Me:> " + msgField.getText()+ "\r\n");
                msgField.setText("");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(msgField);
        buttonPane.add(authButton);

        frame.add(msgScroll);
        frame.add(chatScroll);
        frame.getContentPane().add(BorderLayout.CENTER, chat);
        frame.getContentPane().add(BorderLayout.PAGE_END, buttonPane);

        frame.setVisible(true);

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(applicationContext.getBean("chatClientInitializer", ChatClientInitializer.class));
            channel = bootstrap.connect(host, port).sync().channel();

            while (true) {

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public void setClientSize(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    public void updateChat(String message) {
        if (message != null) {
            chat.append(message + "\r\n");
        }
    }
}
