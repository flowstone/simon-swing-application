package me.xueyao;

import me.xueyao.controller.DialogController;
import me.xueyao.domain.HttpClientResult;
import me.xueyao.util.ApiRequest;
import org.apache.http.HttpStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Simon.Xue
 * @date 2019-12-30 15:12
 **/
public class SwingApplication{

    private JFrame jFrame;
    private String applicationTitle = "测试窗口";
    private Integer applicationWidth = 500;
    private Integer applicationHeight = 450;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private String COMMAND_OK = "OK";

    private JButton loginBtn;
    private JLabel usernameError;
    private JLabel passwordError;


    public SwingApplication() {
        initWindow();
    }

    /**
     * 初始化窗口
     */
    public void initWindow() {
        jFrame = new JFrame(applicationTitle);
        jFrame.setSize(applicationWidth, applicationHeight);
        Dimension dimension = new Dimension();
        dimension.setSize(200, 100);
        jFrame.setMinimumSize(dimension);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        //jFrame.setResizable(false);

        initPanel();
        initMenuBar();
        alertDialog();
        jFrame.setVisible(true);
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem helpMenuItem = new JMenuItem("点我");
        helpMenu.add(helpMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        jFrame.setJMenuBar(menuBar);
    }

    public void initPanel() {
        //GridLayout rootLayout = new GridLayout(3, 1);
        GridLayout layout = new GridLayout(7, 1);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("管理系统");
        titlePanel.add(titleLabel);

        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("用户名");
        usernameError = new JLabel();
        usernameInput = new JTextField();
        usernameInput.setColumns(15);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameInput);
        usernamePanel.add(usernameError);



        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("密 码");
        passwordError = new JLabel();
        passwordInput = new JPasswordField();
        passwordInput.setColumns(15);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
        passwordPanel.add(passwordError);

        JPanel loginPanel = new JPanel();
        loginBtn = new JButton("登陆");
        loginBtn.setActionCommand(COMMAND_OK);
        loginPanel.add(loginBtn);

        jFrame.add(titleLabel);
        jFrame.add(titlePanel);
        jFrame.add(usernamePanel);
        jFrame.add(passwordPanel);
        jFrame.add(loginPanel);
        jFrame.setLayout(layout);
    }


    public void alertDialog(){

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                DialogController dialogController = new DialogController();
                if (COMMAND_OK.equals(actionCommand)) {
                    String username = usernameInput.getText();
                    if (null == username || username.length() == 0) {
                        dialogController.defaultDialog("警告", "用户名不能为空");
                        return;
                    }
                    char[] password = passwordInput.getPassword();
                    if (password.length == 0) {
                        dialogController.defaultDialog("警告", "密码不能为空");
                        return;
                    }

                    HttpClientResult login = ApiRequest.login(username, String.valueOf(password));
                    if (HttpStatus.SC_OK == login.getCode()) {
                        dialogController.defaultDialog("登陆成功", login.getContent());
                        return;
                    }
                }
                dialogController.defaultDialog("登陆成功", "");
            }
        });
    }

    public void initErrorField() {
        usernameError = new JLabel();
        passwordError = new JLabel();
    }

    public static void main(String[] args) {
        new SwingApplication();
    }
}
