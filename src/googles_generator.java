import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
// Teendőlista(ToDo):
// Alap ablak elkészítése DONE
// Szemüveg forma összeállítása DONE
// Oldal/Felülnézet összeállítása DONE
// Menü összeállítása IN PROGRESS, TODO
// Szemüveg megrajzolása, egyik gombbal TODO
// Fájlformátum kitalálása (txt,dat)? TODO
// Súgó, névjegy megírása TODO
// Tesztelés TODO




class FileHandler extends JFileChooser{
    public static boolean SaveFile(){ //Fájl létrehozó/Mentő
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"))); // alapkönyvtár kiválasztása
        int result = fileChooser.showSaveDialog(null);
        File selectedFile = null; // alapértelmezetten semmi, lekezelést kíván
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Saved file: " + selectedFile.getAbsolutePath()); // log check
            return true;
        }
        return false;
    }
    public static File GetFile(){ // Fájlválasztó
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        File selectedFile = null; // alapértelmezetten semmi, lekezelést kíván
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath()); // log check
        }
        return selectedFile;
    }
}

class Google extends JComponent{
    
    private Color szin; // a szemüveg színe
    // szorzószámok, lehetőség szerint 0.5 és 2 között
    // a szemüveg X,Y irányú méretét szeretnék hivatkozni
    private double meretX;
    private double meretY;


    public Google(Color szin,int x, int y) {
        this.szin = szin;
        setLocation(x,y);
        setSize((int)(40*meretX),(int)(20*meretY));
    }
    // A szemüveg megrajzolása, igazítva az ablak méretéhez, negyedekben elosztva a nézeteket
    public void paintFull(Graphics g) {
        super.paintComponent(g);
        
        // Oldalnézet rajzolása
        drawSideView(g, 10, 10, getWidth() / 2 - 20, getHeight() / 2 - 20);
        
        // Felülnézet rajzolása
        drawTopView(g, getWidth() / 2 + 10, 10, getWidth() / 2 - 20, getHeight() / 2 - 20);
        
        // Szemből nézet rajzolása
        drawFrontView(g, 10, getHeight() / 2 + 10, getWidth() - 20, getHeight() / 2 - 20);
    }
    
    public void drawSideView(Graphics g, int x, int y, int width, int height) {
        g.setColor(szin);
        g.fillRect(x + 20, y + 20, width - 40, 10); // Felső keret
        g.fillRect(x + 20, y + 40, width - 40, 10); // Alsó keret
        g.fillRect(x + 10, y + 20, 10, height - 40); // Bal oldali keret
        g.fillRect(x + width - 20, y + 20, 10, height - 40); // Jobb oldali keret
        
        g.setColor(Color.gray);
        g.fillOval(x + 30, y + 30, width / 4, height / 4); // Bal szem
        g.fillOval(x + width / 2 + 10, y + 30, width / 4, height / 4); // Jobb szem
    }
    
    public void drawTopView(Graphics g, int x, int y, int width, int height) {
        g.setColor(szin);
        g.fillRect(x + 20, y + 30, width - 40, 10); // Felső keret
        g.fillRect(x + 20, y + 60, width - 40, 10); // Alsó keret
        g.fillRect(x + 10, y + 30, 10, height - 40); // Bal oldali keret
        g.fillRect(x + width - 20, y + 30, 10, height - 40); // Jobb oldali keret
        
        g.setColor(Color.gray);
        g.fillOval(x + 30, y + 35, width / 4, height / 4); // Bal szem
        g.fillOval(x + width / 2 + 10, y + 35, width / 4, height / 4); // Jobb szem
    }
    
    public void drawFrontView(Graphics g, int x, int y, int width, int height) {
        g.setColor(szin);
        g.fillRect(x + 20, y + 20, width - 40, 10); // Felső keret
        g.fillRect(x + 20, y + 40, width - 40, 10); // Alsó keret
        g.fillRect(x + 10, y + 20, 10, height - 40); // Bal oldali keret
        g.fillRect(x + width - 20, y + 20, 10, height - 40); // Jobb oldali keret
        
        g.setColor(Color.gray);
        g.fillOval(x + 30, y + 25, width / 4, height / 4); // Bal szem
        g.fillOval(x + width / 2 + 10, y + 25, width / 4, height / 4); // Jobb szem
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}

class MainWindow extends JFrame implements KeyListener {
    Container cp = getContentPane();

    public MainWindow() {
        setTitle("Main Window"); // cím
        setDefaultCloseOperation(EXIT_ON_CLOSE); // becsukod mit csinál
        setResizable(false); // nem átméretezhető
        setSize(800,600); // alap méret
        cp.setBackground(Color.LIGHT_GRAY); // alap háttérszín
        cp.setLayout(null);
        addKeyListener(this); // figyeli, ha lenyomsz valamit
        setVisible(true); // ettől látszik a keret
    }

    public void addItem(Google google) {
        cp.add(google);
    }
    public void keyTyped(KeyEvent e) {}
    // Ha lenyomtak egy billentyűt, ide kerül a vezérlés:
    public void keyPressed(KeyEvent e){
      if (e.getKeyCode()== KeyEvent.VK_ESCAPE) // ECS-nél megáll a progi
        System.exit(0); // program exit, mindent bezár, megszüntet
    }
    public void keyReleased(KeyEvent e) {}
}

class MainMenu extends JMenuBar{
    public MainMenu() {
        // Megnyitás
        JMenu menu_open = new JMenu("Megnyitás");
        JMenuItem menu_open_new = new JMenuItem("Új fájl",new ImageIcon("icons/new.gif"));
        menu_open.add(menu_open_new);
        JMenuItem menu_open_exist = new JMenuItem("Meglévő fájl");
        menu_open.add(menu_open_exist);
        // Mentés
        JMenu menu_save = new JMenu("Mentés");
        // Random
        JMenu menu_random = new JMenu("Random");
        JMenuItem menu_random_size = new JMenuItem("Méret");
        menu_random.add(menu_random_size);
        JMenuItem menu_random_color = new JMenuItem("Szín");
        menu_random.add(menu_random_color);
        // Súgó
        JMenu menu_helper = new JMenu("Súgó");
        JMenuItem menu_helper_use = new JMenuItem("Használat",new ImageIcon("icons/help.gif"));
        JMenuItem menu_helper_about = new JMenuItem("Névjegy");
        menu_helper.add(menu_helper_use);
        menu_helper.add(menu_helper_about);
        this.add(menu_open);
        this.add(menu_save);
        this.add(menu_random);
        this.add(menu_helper);
        // A menü metódusai
        // Megnyitás
        menu_open_new.addActionListener(new ActionListener() {// Új fájl
            public void actionPerformed(ActionEvent actionEvent){
                if(FileHandler.SaveFile() == true){ // TODO

                }
                else{

                }
            }  
          });
        menu_open_exist.addActionListener(new ActionListener() {//Meglévő fájl
            public void actionPerformed(ActionEvent actionEvent){
                if(FileHandler.GetFile() != null){ // TODO

                }
                else{

                }
            }  
            });
        // Mentés
        menu_save.addActionListener(new ActionListener() { // Mentés
            public void actionPerformed(ActionEvent actionEvent){
                if(FileHandler.SaveFile() == true){ // TODO

                }
                else{

                }  
            }  
            });
        // Random
        menu_random_size.addActionListener(new ActionListener() { // Random méret
            public void actionPerformed(ActionEvent actionEvent){// TODO

            }  
            });
        menu_random_color.addActionListener(new ActionListener() { // Random szín
            public void actionPerformed(ActionEvent actionEvent){// TODO

            }  
            });
        // Súgó
        menu_helper_use.addActionListener(new ActionListener() { // Használat
            public void actionPerformed(ActionEvent actionEvent){//TODO

            }  
            });
        menu_helper_about.addActionListener(new ActionListener() { // Névjegy
            public void actionPerformed(ActionEvent actionEvent){// TODO

            }  
            });
    }
}





public class googles_generator {
    private MainWindow mainWindow;
    private MainMenu mainMenu;
    // A program fő futásának helye, "kiszervezve" a main-ből
    public void Refresh(MainWindow mainWindow){
        mainWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    googles_generator() {
        mainWindow = new MainWindow();
        mainMenu = new MainMenu();
        mainWindow.setJMenuBar(mainMenu);
        Refresh(mainWindow);
    }
    public static void main(String[] args){
        new googles_generator();
    }
}
