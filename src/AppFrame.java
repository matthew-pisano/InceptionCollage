import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppFrame extends Main{
    private static HashMap<BufferedImage, Integer> tiles;
    private static ArrayList<BufferedImage> paths;
    private static String mainPath;
    private static JProgressBar progressBar;
    private static JTextArea progressText;
    private static JTextArea mainImage;
    private static JTextArea outputImage;
    private static JTextArea tilesImage;
    private static JTextArea widthIn;
    private static JTextArea heightIn;
    private static JTextArea tileWidth;
    private static JTextArea outTitle;
    private static int setPathType;

    public AppFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPathType = 0;
        paths = new ArrayList<>(0);
        Container contentPane = getContentPane();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        contentPane.setLayout(gridbag);
        JTextArea messages = new JTextArea();
        int yAt = 1;


        /*JPanel background = new JPanel();
        background.setBackground(new Color(73, 222, 159));
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 7;
        contentPane.add(background, c);
        c = new GridBagConstraints();*/

        mainImage = new JTextArea();
        mainImage.setColumns(25);
        mainImage.setBackground(new Color(220, 220, 220));
        //mainImage.setText("C:/Users/agent/Desktop/Kyliw/IMG_20191221_180101_308.jpg");
        //mainImage.setPreferredSize(new Dimension(50, 15));
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(mainImage, c);
        c = new GridBagConstraints();

        JTextArea mainImgTitle = new JTextArea();
        mainImgTitle.setText("Background Path:");
        mainImgTitle.setColumns(20);
        mainImgTitle.setOpaque(false);
        //mainImgTitle.setBackground(new Color(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(mainImgTitle, c);
        c = new GridBagConstraints();

        JButton mainExplorer = new JButton("Choose File");
        mainExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPathType = 0;
                FileExplorer fileExplorer = new FileExplorer("File Explorer", true);
                fileExplorer.createWindow();
            }
        });
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = yAt;
        contentPane.add(mainExplorer, c);
        c = new GridBagConstraints();
        yAt+=1;

        tilesImage = new JTextArea();
        //tiles.setColumns(25);
        /*tilesImage.setText("C:/Users/agent/Desktop/Kyliw/IMG_20191221_174454_313.jpg\n" +
                "C:/Users/agent/Desktop/Kyliw/IMG_20200614_164913_558.jpg\n" +
                "C:/Users/agent/Desktop/Kyliw/IMG_20200112_113335_668.jpg\n" +
                "C:/Users/agent/Desktop/Kyliw/IMG_20200311_100218_476.jpg\n" +
                "C:/Users/agent/Desktop/Kyliw/IMG_20200323_222326_359.jpg");*/
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(tilesImage, c);
        c = new GridBagConstraints();

        JTextArea tilesTitle = new JTextArea();
        tilesTitle.setText("Tiles Path(s):");
        tilesTitle.setColumns(20);
        tilesTitle.setOpaque(false);
        //tilesTitle.setBackground(new Color(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(tilesTitle, c);
        c = new GridBagConstraints();

        JButton tilesExplorer = new JButton("Choose File");
        tilesExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPathType = 1;
                FileExplorer fileExplorer = new FileExplorer("File Explorer", true);
                fileExplorer.createWindow();
            }
        });
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = yAt;
        contentPane.add(tilesExplorer, c);
        c = new GridBagConstraints();
        yAt+=1;

        outputImage = new JTextArea();
        outputImage.setColumns(25);
        //outputImage.setText("C:/Users/agent/Desktop/Kyliw");
        outputImage.setBackground(new Color(220, 220, 220));
        //mainImage.setPreferredSize(new Dimension(50, 15));
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(outputImage, c);
        c = new GridBagConstraints();

        JTextArea outputTitle = new JTextArea();
        outputTitle.setText("Output Path:");
        outputTitle.setColumns(20);
        outputTitle.setOpaque(false);
        //outputTitle.setBackground(new Color(0, 0, 0, 0));
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(outputTitle, c);
        c = new GridBagConstraints();

        JButton outputExplorer = new JButton("Choose File");
        outputExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPathType = 2;
                FileExplorer fileExplorer = new FileExplorer("File Explorer", false);
                fileExplorer.createWindow();
            }
        });
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = yAt;
        contentPane.add(outputExplorer, c);
        c = new GridBagConstraints();
        yAt+=1;

        outTitle = new JTextArea();
        outTitle.setBackground(new Color(220, 220, 220));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(outTitle, c);
        c = new GridBagConstraints();

        JTextArea outTitleTitle = new JTextArea();
        outTitleTitle.setColumns(20);
        outTitleTitle.setOpaque(false);
        outTitleTitle.setText("Title:");
        outTitleTitle.setEditable(false);
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(outTitleTitle, c);
        c = new GridBagConstraints();

        JTextArea outTitleExt = new JTextArea();
        outTitleExt.setColumns(10);
        outTitleExt.setOpaque(false);
        outTitleExt.setText(".bmp");
        outTitleExt.setEditable(false);
        c.gridx = 2;
        c.gridy = yAt;
        contentPane.add(outTitleExt, c);
        c = new GridBagConstraints();
        yAt+=1;

        widthIn = new JTextArea();
        widthIn.setText("5000");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(widthIn, c);
        c = new GridBagConstraints();

        JTextArea widthInTitle = new JTextArea();
        widthInTitle.setColumns(20);
        widthInTitle.setOpaque(false);
        widthInTitle.setText("Width (Px):");
        widthInTitle.setEditable(false);
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(widthInTitle, c);
        c = new GridBagConstraints();
        yAt+=1;

        heightIn = new JTextArea();
        heightIn.setText("5000");
        heightIn.setBackground(new Color(220, 220, 220));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(heightIn, c);
        c = new GridBagConstraints();

        JTextArea heightInTitle = new JTextArea();
        heightInTitle.setColumns(20);
        heightInTitle.setOpaque(false);
        heightInTitle.setText("Height (Px):");
        heightInTitle.setEditable(false);
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(heightInTitle, c);
        c = new GridBagConstraints();
        yAt+=1;

        tileWidth = new JTextArea();
        tileWidth.setText("50");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(tileWidth, c);
        c = new GridBagConstraints();

        JTextArea tileWidthTitle = new JTextArea();
        tileWidthTitle.setColumns(20);
        tileWidthTitle.setOpaque(false);
        tileWidthTitle.setText("Tile Width (Px):");
        tileWidthTitle.setEditable(false);
        c.gridx = 0;
        c.gridy = yAt;
        contentPane.add(tileWidthTitle, c);
        c = new GridBagConstraints();
        yAt+=1;

        JButton start = new JButton("Begin");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPath = mainImage.getText();
                paths = new ArrayList<>(0);
                String tilePaths = tilesImage.getText();
                boolean valid = tilePaths.length() > 0;
                for(int i=0; i<tilePaths.length(); i++){
                    if(tilePaths.indexOf("\n", i) != -1){
                        String path = tilePaths.substring(i, tilePaths.indexOf("\n", i));
                        BufferedImage img = null;
                        try {
                            img = ImageIO.read(new File(path));
                        } catch (IOException e) { e.printStackTrace(); }
                        if(img == null) continue;
                        paths.add(img);
                        valid = valid && new File(path).exists();
                        i = tilePaths.indexOf("\n", i)+1;
                        System.out.println("AddedPath: "+path+", "+path.length());
                    }else{
                        String path = tilePaths.substring(i);
                        BufferedImage img = null;
                        try {
                            img = ImageIO.read(new File(path));
                        } catch (IOException e) { e.printStackTrace(); }
                        if(img == null) continue;
                        paths.add(img);
                        valid = valid && new File(path).exists();
                        System.out.println("AddedPath: "+path+", "+path.length());
                        break;
                    }
                }
                valid = valid && new File(mainPath).exists();
                try{
                    valid = valid && Integer.parseInt(widthIn.getText()) > 200;
                    valid = valid && Integer.parseInt(heightIn.getText()) > 200;
                }catch (NumberFormatException e){
                    valid = false;
                    e.printStackTrace();
                }
                if(!valid){
                    messages.setText("Not all file paths are correct or some inputs are invalid!");
                    return;
                }
                new Thread() {
                    @Override
                    public void run(){
                        begin();
                        progressBar.setValue(0);
                        progressText.setText("");
                        messages.setText("Complete!");
                    }
                }.start();
            }
        });
        c.insets = new Insets(20, 70, 90, 50); //top left bottom right//
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(start, c);
        c = new GridBagConstraints();


        progressBar = new JProgressBar();
        c.gridx = 1;
        c.gridy = yAt;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(progressBar, c);
        c = new GridBagConstraints();

        progressText = new JTextArea();
        progressText.setEditable(false);
        progressText.setBackground(new Color(0, 0, 0, 0));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(progressText, c);
        c = new GridBagConstraints();
        yAt+=1;

        messages.setEditable(false);
        messages.setOpaque(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = yAt;
        contentPane.add(messages, c);
        c = new GridBagConstraints();
        yAt+=1;

        /*

        imperium = new JButton("Make Imperium Map");
        imperium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = new GridBagger(true, europeMap);
                mapWindow = window;
                System.out.println(europeMap);
                window.inAnApplet = false;
                window.setTitle("Earf");
                window.pack();
                window.setVisible(true);
                setVisible(false);
            }
        });
        c.insets = new Insets(90, 70, 50, 70);
        c.gridx = 1;
        c.gridy = 0;
        gridbag.setConstraints(imperium, c);
        contentPane.add(imperium);



        loader = new JButton("Load Map");
        loader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window = new GridBagger(true, europeMap);
                mapWindow = window;
                window.loadState(mainImage.getText().toString());
                System.out.println("loaded");
                window.inAnApplet = false;
                window.setTitle("Earf");
                window.pack();
                window.setVisible(true);
                setVisible(false);
            }
        });
        c.insets = new Insets(90, 70, 0, 70);
        c.gridx = 2;
        c.gridy = 1;
        gridbag.setConstraints(loader, c);
        contentPane.add(loader);*/
    }
    public static void setMainPath(String path){
        mainImage.setText(path);
    }
    public static void setOutputPath(String path){
        outputImage.setText(path);
    }
    public static void addTilesPath(String path){
        if(tilesImage.getText().length() > 0)
            tilesImage.setText(tilesImage.getText()+"\n"+path);
        else tilesImage.setText(path);
    }
    public static int getSetPathType(){
        return setPathType;
    }
    public static void begin(){
        tiles = new HashMap<>(0);
        for(BufferedImage img : paths) {
            if (img != null) {
                Color avg = new Color(avarageColor(img));
                System.out.println("avgcolor: " + avg.getRed() + ", " + avg.getGreen() + ", " + avg.getBlue());
                ColorConvertOp colorConvert =
                        new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
                colorConvert.filter(img, img);
                tiles.put(img, avg.getRGB());
            }

        }

        BufferedImage image = null;
        BufferedImage scaled = new BufferedImage(Integer.parseInt(widthIn.getText()), Integer.parseInt(heightIn.getText()), BufferedImage.TYPE_INT_RGB);
        try {
            image = ImageIO.read(new File(mainPath));
        } catch (IOException e) { e.printStackTrace(); }
        System.out.print("Converting...");
        if (image != null) {
            Graphics2D g = scaled.createGraphics();
            g.drawImage(image, 0, 0, scaled.getWidth(), scaled.getHeight(), null);
            lowerResolution(scaled, false, Integer.parseInt(tileWidth.getText()));
        }
        String outPath = outputImage.getText()+"/"+outTitle.getText()+".bmp";
        File outputfile = new File(outPath);
        //if(!outputfile.exists()) outputfile.mkdir();
        System.out.println(scaled.getWidth());
        try {
            ImageIO.write(scaled, "bmp", outputfile);
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println(new File(outPath).exists());
    }

    public static int avarageColor(BufferedImage image) {
        int averageR = 0, averageG = 0, averageB = 0;
        ArrayList<Color> colors = new ArrayList<>(0);
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++)
                colors.add(new Color(image.getRGB(x, y)));

        for (Color c : colors) {
            averageR += c.getRed();
            averageG += c.getGreen();
            averageB += c.getBlue();
        }
        averageR /= colors.size();
        averageG /= colors.size();
        averageB /= colors.size();

        return new Color(averageR, averageG, averageB).getRGB();

    }

    public static void lowerResolution(BufferedImage image, boolean pixelate, int tileWidth){
        BufferedImage refrence = image.getSubimage(0, 0, image.getWidth(), image.getHeight());
        for(int x=0; x<image.getWidth()-2; x+=tileWidth){
            for(int y = 0; y<image.getHeight()-2; y+=tileWidth){
                int pixColor = refrence.getRGB(x+1, y+1);
                //System.out.print(x+", "+y);
                int percent = (int) (((double)x/image.getWidth()-(1-(double)y/image.getHeight())/image.getWidth())*100);
                //System.out.println("Percent Complete: "+percent+"%");
                progressText.setText(percent+"%");
                progressBar.setValue(percent);
                Graphics2D g = image.createGraphics();
                if(pixelate){
                    g.setColor(new Color(pixColor));
                }else{
                    Color c = new Color(pixColor);
                    int avgScore = Integer.MAX_VALUE;
                    BufferedImage pathWinner = null;
                    for(BufferedImage img : paths){
                        Integer pathColor = tiles.get(img);
                        int rDiff = Math.abs(c.getRed()-new Color(pathColor).getRed());
                        int gDiff = Math.abs(c.getBlue()-new Color(pathColor).getBlue());
                        int bDiff = Math.abs(c.getGreen()-new Color(pathColor).getGreen());
                        int avgDiff = (rDiff+gDiff+bDiff)/3;
                        if(avgDiff < avgScore){
                            avgScore = avgDiff;
                            pathWinner = img;
                        }
                    }
                    g.drawImage(pathWinner, x, y, tileWidth, tileWidth, null);
                    g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 170));
                    //g.setColor(c);
                }
                g.fillRect(x, y, tileWidth, tileWidth);
                g.dispose();

                /*for(int x2 = x; x2 <=x+pixelGrow; x2++){
                    for(int y2 = y; y2 <=y+pixelGrow; y2++){
                        if(x2 != x && y2 != y){
                            image.setRGB(x2, y2, pixColor);
                        }
                    }
                }*/
            }
        }
    }
}
