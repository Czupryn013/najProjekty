package pl.czupryn.Klocek;

import javax.swing.*;

public class OknoGry extends JFrame {

    public OknoGry() {
        this.add(new PanelGry());
        this.setTitle("Klocek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
