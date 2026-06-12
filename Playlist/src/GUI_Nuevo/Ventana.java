package GUI_Nuevo;

import java.io.File;

import Recursos.NodoDoble;
import ReproductorNuevo.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Ventana extends Application {

    private NodoDoble nodoActual;  // renombrado para claridad

    private Button button_addFiles;
    private Button button_play;
    private Button button_pause;
    private Button button_stop;
    private Button button_next;
    private Button button_prev;

    private Button button_ordenTitulo;
    private Button button_ordenArtista;
    private Button button_ordenAnio;
    private Button button_sinOrden;

    private ReproductorMP3 reproductor;
    private ListaCanciones playlist;
    private ListaCancionesOrd playlistOrd;
    private LectorMP3 lector;

    private boolean modoOrdenado;
    private String orden_criterio;

    @Override
    public void start(Stage stage) {
        inicializarComponentes();
        Scene escena = new Scene(crearLayout(), 600, 400);
        stage.setTitle("Reproductor MP3");
        stage.setScene(escena);
        stage.show();
    }

    private void inicializarComponentes() {
        this.reproductor = new ReproductorMP3();
        this.playlist = new ListaCanciones();
        this.lector = new LectorMP3();
        this.nodoActual = null; // lista vacía al inicio
        this.orden_criterio = null;
        this.modoOrdenado = false;


        this.button_addFiles = new Button("Add Files");
        this.button_prev = new Button("<<");
        this.button_play = new Button("Play");
        this.button_pause = new Button("Pause");
        this.button_stop = new Button("Stop");  // ← ya inicializado
        this.button_next = new Button(">>");

        this.button_ordenTitulo = new Button("Titulo");
        this.button_ordenArtista = new Button("Artista");
        this.button_ordenAnio = new Button("Anio");
        this.button_sinOrden = new Button("Sin Orden");

        registrarEventos();
    }

    private void registrarEventos() {

        // Agregar canción 
        button_addFiles.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos MP3", "*.mp3")
            );
            
            File archivo = fc.showOpenDialog(null);
            if (archivo != null) {
                Cancion cancion = lector.leer(archivo);
                if (cancion != null) {
                    // Una sola lista como fuente de verdad
                    this.playlist.insertar(cancion, this.playlist.tamanio());
                    System.out.println("Cancion agregada: " + cancion);
                } else {
                    System.out.println("No se pudo leer el archivo.");
                }
            }
        });

        // ── Play ─────────────────────────────────────────────
        button_play.setOnAction(e -> {
            // Si no hay nodo activo, arrancar desde el frente
            if (this.nodoActual == null) {
                this.nodoActual = this.playlist.getFrenteL();
            }
            else {
                if (this.nodoActual == null) {
                    System.out.println("No hay canciones en la lista.");
                }
                else {
                    Cancion cancion = (Cancion) this.nodoActual.getNodoInfo();
                    this.reproductor.reproducir(cancion);
                }
            }
        });

        // Siguiente 
        button_next.setOnAction(e -> {
            if (this.nodoActual != null && this.nodoActual.getNextNodo() != null) {
                this.nodoActual = this.nodoActual.getNextNodo();

                //Reproduce la cancion siguiente automaticamente....

                Cancion cancion = (Cancion) this.nodoActual.getNodoInfo();
                this.reproductor.reproducir(cancion);
            } else {
                System.out.println("No hay cancion siguiente.");
            }
        });

        // Anterior 
        button_prev.setOnAction(e -> {
            if (this.nodoActual != null && this.nodoActual.getPrevNodo() != null) {
                this.nodoActual = this.nodoActual.getPrevNodo();

                //Reproduce la cancion anterior automaticamente....

                Cancion cancion = (Cancion) this.nodoActual.getNodoInfo();
                this.reproductor.reproducir(cancion);
            } else {
                System.out.println("No hay cancion anterior.");
            }
        });

        // ── Pausar / Detener ─────────────────────────────────
        button_pause.setOnAction(e -> this.reproductor.pausar());
        button_stop.setOnAction(e  -> this.reproductor.detener());

        button_ordenTitulo.setOnAction(e -> activarOrden("titulo"));
        button_ordenArtista.setOnAction(e -> activarOrden("artista"));
        button_ordenAnio.setOnAction(e -> activarOrden("anio"));
        button_sinOrden.setOnAction(e -> desactivarOrden());
    }

    private VBox crearLayout() {
        HBox addFilesBox = new HBox(10, button_addFiles);
        addFilesBox.setAlignment(Pos.TOP_LEFT);

        HBox controles = new HBox(10,
            button_prev, button_play, button_pause, button_stop, button_next
        );
        controles.setAlignment(Pos.CENTER);

        HBox orden = new HBox(10,
            button_ordenAnio, button_ordenArtista, button_ordenTitulo, button_sinOrden
        );

        VBox root = new VBox(20, addFilesBox, controles, orden); //<-- añadir funcionalidad correctamente...
        root.setAlignment(Pos.CENTER);
        return root;
    }

    private void activarOrden(String criterio) {
        this.modoOrdenado = true;
        this.playlistOrd.ordenarPor(criterio); //Ordena la lista, segun el criterio...
        this.nodoActual = this.playlistOrd.getFrenteL(); //Apunta a la lista ordenada...

    }

    private void desactivarOrden() {
        this.modoOrdenado = false;
        this.nodoActual = this.playlist.getFrenteL();
    }

    private void reproducirNodoActual() {

        if(this.nodoActual == null) {
            System.out.println("No hay cancion a reproducir...");
        }
        else{
            Cancion c = (Cancion) this.nodoActual.getNodoInfo();
            System.out.println("Reproduciendo: " + c);
            this.reproductor.reproducir(c);   
        }
    }






}