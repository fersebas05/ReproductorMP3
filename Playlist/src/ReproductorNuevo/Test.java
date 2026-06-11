package ReproductorNuevo;

import java.io.File;

import javafx.embed.swing.JFXPanel;

public class Test {
    public static void main(String[] args) {
        // ListaCanciones A = new ListaCanciones();
        // ListaCancionesOrd B = new ListaCancionesOrd(ListaCancionesOrd.ordenCriterio.TITULO);
        
        new JFXPanel();

        LectorMP3 lector = new LectorMP3();

        Cancion c1 = lector.leer(new File("C:\\Users\\sebas\\Downloads\\WOS - CONTANDO OVEJAS (Concept Video)(MP3_320K).mp3"));

        Cancion c2 = lector.leer(new File("C:\\Users\\sebas\\Downloads\\Bruno Mars - Runaway Baby (Official Lyric Video).mp3"));

        ReproductorMP3 mp3 = new ReproductorMP3();

        mp3.agregarCancion(c1);
        mp3.agregarCancion(c2);

        mp3.reproducir();

    }

}
