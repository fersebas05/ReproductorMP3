package GUI_Nuevo;

import ReproductorNuevo.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Ventana extends Application{
    private Button button_addFiles;
    
    private Button button_play;
    private Button button_pause;

    private Button button_next;
    private Button button_prev;

    private ReproductorMP3 reproductor;
    private ListaCanciones playlist;
    private ListaCancionesOrd playlistOrd;
    private LectorMP3 lector;

    private Button aleatorio;
    
    private Label labelCancionActual;
    private ListView<String> listViewPlayList;

    public void start(Stage stage) {
        inicializarComponentes();

        Scene escena = new Scene(crearLayout(), 600, 400);

        stage.setTitle("Reproductor MP3 de musica");
        stage.setScene(escena);
        stage.show();
    }

    private void inicializarComponentes() {
        this.reproductor = new ReproductorMP3();
        this.playlist = new ListaCanciones();
        this.playlistOrd = new ListaCancionesOrd();
        this.lector = new LectorMP3();


        this.button_addFiles = new Button("Add Files");

        
        this.button_prev = new Button("<<");
        this.button_play = new Button("Play");
        this.button_pause = new Button("Pause");
        this.button_next = new Button(">>");


    }

}
