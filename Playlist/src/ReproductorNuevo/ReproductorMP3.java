package ReproductorNuevo;

import Recursos.NodoDoble;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ReproductorMP3 {
    private NodoDoble nodoActual;
    private ListaCanciones playlist;
    private MediaPlayer mediaPlayer;

    public ReproductorMP3() {
        this.nodoActual = null;
        this.playlist = new ListaCanciones();
        this.mediaPlayer = null;
    }
    
    public void agregarCancion(Cancion cancion) {
        this.playlist.insertar(cancion, this.playlist.tamanio());
        cargarCancionActual(cancion);
    }

    public void reproducir() {
        if(this.nodoActual == null) {
            this.nodoActual = this.playlist.getFrenteL();
        }
        
        Cancion cancion = (Cancion) nodoActual.getNodoInfo();
        if(cancion != null) {
            if(this.mediaPlayer == null) {
                cargarCancionActual(cancion);
            }
            this.mediaPlayer.play();
        }
        else {
            System.out.println("No hay cancion para reproducir");
        }
    }

    // public void reproducir() {
    //     if(this.nodoActual == null) {
    //         this.nodoActual = this.playlist.getFrenteL();
    //     }

    //     if(this.nodoActual != null) {
    //         Cancion cancion = (Cancion) this.nodoActual.getNodoInfo();

    //         System.out.println("Reproduciendo " + cancion);

    //         try {
    //             if(this.mediaPlayer != null) { 
    //                 this.mediaPlayer.stop();
    //                 this.mediaPlayer.dispose();
    //                 this.mediaPlayer = null;
    //             }
    //             Media media = new Media(cancion.getRuta());
    //             mediaPlayer = new MediaPlayer(media);

    //             mediaPlayer.play();

    //         } catch(Exception e) {
    //             System.out.println("Error al cargar la cancion por: " +  e.getMessage().toString());
    //             this.mediaPlayer = null;
    //         }
    //     }
    // }

    //Carga el MediaPlayer de forma segura aislando fallos de lectura o metadatos.
    private void cargarCancionActual(Cancion cancion) {
        try {
            Media media = new Media(cancion.getRuta()); 
            this.mediaPlayer = new MediaPlayer(media);

            // Al terminar de reproducirse, pasa de manera natural a la siguiente
            this.mediaPlayer.setOnEndOfMedia(() -> {
                this.siguiente();
            });

        } catch (Exception e) {
            System.err.println("Error al cargar los metadatos o el archivo de audio: " + e.getMessage());
            this.mediaPlayer = null;
        }
    }

    public void mostrar() {
        Cancion c1 = (Cancion) nodoActual.getNodoInfo();
        System.out.println("Cancion: " +  c1.toString());
    }

    public void pausar() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.pause();
        }
    }

    public void detener() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
        }
    }

    public void siguiente() {
        if(this.nodoActual != null && this.nodoActual.getNextNodo() != null) {
            this.nodoActual = this.nodoActual.getNextNodo();
            reproducir();
        }
        else {
            System.out.println("No hay cancion siguiente a reproducir...");
        }
    }

    public void anterior() {
        if (this.nodoActual != null && this.nodoActual.getPrevNodo() != null) {
            this.nodoActual = this.nodoActual.getPrevNodo();
            reproducir();
        }
        else{
            System.out.println("No hay cancion anterior a reproducir...");
        }
    }

    public int cantidadCanciones() {
        return this.playlist.tamanio();
    }

}
