import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileExplorer extends Main{
    private String title;
    private JPanel fileLayout;
    private JButton select;
    private boolean folderSelected;
    private static String currentPath;
    private boolean alreadySelceted;
    JTextField pathTextField;
    private long lastAction;
    private boolean openFile;
    public FileExplorer(String title, boolean openFile){
        this.openFile = openFile;
        this.title = title;
        lastAction = System.currentTimeMillis();
        makeContent();
        if(currentPath == null || currentPath.equals("C:")) currentPath = "C:/users";
        folderContents(currentPath);
    }
    private void makeContent(){
        alreadySelceted = false;
        Container contentPane = getContentPane();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        contentPane.setLayout(gridbag);


        fileLayout = new JPanel();
        fileLayout.setLayout(gridbag);
        fileLayout.setBackground(new Color(220, 220, 220));

        JButton fileUp = new JButton("Up");
        fileUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pathStr = pathTextField.getText();
                if(pathStr.equals("C:/")) return;
                if(pathStr.equals("C:")){
                    folderContents("C:/");
                    return;
                }
                if(pathStr.charAt(pathStr.length()-1) == '/')
                    pathStr = pathStr.substring(0, pathStr.length()-2);
                folderContents(pathStr.substring(0, pathStr.lastIndexOf("/")+1));
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = .1;
        add(fileUp, c);
        c = new GridBagConstraints();

        pathTextField = new JTextField();
        pathTextField.setEditable(false);
        pathTextField.setColumns(25);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = .4;
        c.gridy = 0;
        c.gridx = 1;
        add(pathTextField, c);
        c = new GridBagConstraints();

        JScrollPane fileScroll = new JScrollPane(fileLayout);
        fileScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fileScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        fileScroll.setPreferredSize(new Dimension(150,150));
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 2;
        c.ipady = 200;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 0;
        add(fileScroll, c);
        c = new GridBagConstraints();


        if(openFile) select = new JButton("Open");
        else select = new JButton("Select");
        c.anchor = GridBagConstraints.SOUTHEAST;
        c.gridy = 2;
        c.gridx = 1;
        add(select, c);
    }
    private void folderContents(String path){
        System.out.println("Open folder: "+path);
        fileLayout.removeAll();
        fileLayout.updateUI();
        currentPath = path;
        pathTextField.setText(currentPath);
        File folder = new File(path);
        String[] dirs = folder.list();
        if(dirs == null) return;
        GridBagConstraints c = new GridBagConstraints();
        for (int i = 0; i < dirs.length; i++) {
            boolean rightExt = (dirs[i].toLowerCase().endsWith("jpg") || dirs[i].toLowerCase().endsWith("png")) && openFile;
            if(dirs[i].contains(".") && !rightExt) continue;
            JButton file = new JButton();
            file.setText(dirs[i]);
            file.setBorderPainted(true);
            int finalI = i;
            file.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Time: "+System.currentTimeMillis()+", Last Action: "+lastAction);
                    String modPath = path;
                    if(path.charAt(path.length()-1) != '/') modPath = path+"/";
                    if(dirs[finalI].contains(".") || !openFile){
                        currentPath = modPath+dirs[finalI];
                        pathTextField.setText(currentPath);
                        select.setText("Select");
                        select.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(AppFrame.getSetPathType() == 0)
                                    AppFrame.setMainPath(pathTextField.getText());
                                else if (AppFrame.getSetPathType() == 1)
                                    AppFrame.addTilesPath(pathTextField.getText());
                                else
                                    AppFrame.setOutputPath(pathTextField.getText());
                                currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
                                alreadySelceted = true;
                                dispatchEvent(new WindowEvent((FileExplorer.this), WindowEvent.WINDOW_CLOSING));
                            }
                        });
                    }
                    if (!dirs[finalI].contains(".")) {
                        if(System.currentTimeMillis()-lastAction < 500)
                            folderContents(modPath+dirs[finalI]);
                        else{
                            currentPath = modPath+dirs[finalI];
                            pathTextField.setText(currentPath);
                            lastAction = System.currentTimeMillis();
                        }
                        if(openFile) select.setText("Open");
                        select.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(alreadySelceted) return; //stops from looping
                                System.out.println("Open Button");
                                folderContents(currentPath);
                            }
                        });
                    }

                }
            });
            c.gridy = i;
            c.gridx = 1;
            c.anchor = GridBagConstraints.WEST;
            fileLayout.add(file, c);
            JLabel picLabel = new JLabel();
            new Thread() {
                @Override
                public void run() {
                    BufferedImage image = null;
                    try {
                        FileInputStream fis = new FileInputStream(new File(path + "/" + dirs[finalI]));
                        image = ImageIO.read(fis);
                    } catch (IOException | NullPointerException e) { /*e.printStackTrace();*/}
                    System.out.println(path + "/" + dirs[finalI] + " image null " + (image == null));
                    if (image != null) {
                        Image simg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                        picLabel.setIcon(new ImageIcon(simg));

                    }
                }
            }.start();
            picLabel.setPreferredSize(new Dimension(30, 30));
            c.gridy = i;
            c.gridx = 0;
            c.anchor = GridBagConstraints.WEST;
            fileLayout.add(picLabel, c);
        }
    }
    public void createWindow(){
        setTitle(title);
        pack();
        setVisible(true);
    }
}
