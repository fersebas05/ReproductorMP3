package Reproductor;

import java.io.File;

import javafx.embed.swing.JFXPanel;

public class test2 {

    public static void main(String[] args) {

        new JFXPanel();

        LectorMP3 lector = new LectorMP3();

        Cancion c1 = lector.leer(new File("C:\\Users\\sebas\\Downloads\\WOS - CONTANDO OVEJAS (Concept Video)(MP3_320K).mp3"));
        System.out.println(c1);

        Cancion c2 = lector.leer(new File("C:\\Users\\sebas\\Downloads\\Bruno Mars - Runaway Baby (Official Lyric Video).mp3"));
        System.out.println(c2);
 
        PlayList playlist = new PlayList("Musica");

        playlist.insertar(c1, 0);
        playlist.insertar(c2, 1);

        playlist.mostrarPlaylist();

        playlist.ordenar(PlayListAbs.OrdenCriterio.TITULO);

        ReproductorMP3 mp3 = new ReproductorMP3();

        mp3.cargarPlayList(playlist);

        mp3.play();
        
    }
}