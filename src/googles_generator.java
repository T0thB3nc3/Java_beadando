import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    public void keyTyped(KeyEvent e) {}
    // Ha lenyomtak egy billentyűt, ide kerül a vezérlés:
    public void keyPressed(KeyEvent e){
      if (e.getKeyCode()== KeyEvent.VK_ESCAPE) // ECS-nél megáll a progi
        System.exit(0);
    }
    public void keyReleased(KeyEvent e) {}
}

class MainMenu extends JMenuBar {
    public MainMenu() {
        // Megnyitás
        JMenu menu_open = new JMenu("Megnyitás");
        JMenuItem menu_open_new = new JMenuItem("Új fájl");
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
        // a this-el tudsz visszahivatkozni az említett osztályra
        this.add(menu_open);
        this.add(menu_save);
        this.add(menu_random);
        this.add(menu_helper);
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
