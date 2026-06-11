package GUI;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Reproductor.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Ventana extends Application{
    protected Button button_addFiles;

    protected Button button_play;
    protected Button button_pause;
    protected Button button_stop;

    protected Button button_prev;
    protected Button button_next;

    protected ReproductorMP3 reproductor;
    protected PlayList playlist;
    protected LectorMP3 lector;

    protected Button button_ordenarTitulo;
    protected Button button_ordenarArtista; 
    protected Button button_ordenarAnio;

    protected Button button_aleatorio;

    protected Label labelCancionActual;
    protected ListView<String> listViewPlaylist;

    protected Map<String, PlayList> mapaAlbumes;
    protected TreeView<String> treeViewBiblioteca; //<--- Aplicar el cambio aqui

    protected Slider sliderProgreso;
    
    public void start(Stage stage) {
        inicializarComponentes();

        habilitarDragAndDrop(); //Habilita la funcionalidad de arrastrar y soltar archivos en la ventana....

        Scene escena = new Scene(crearLayout(), 600, 400);

        stage.setTitle("Reproductor MP3");
        stage.setScene(escena);
        stage.show();

    }

    private void inicializarComponentes() {
        this.reproductor = new ReproductorMP3();
        this.playlist = new PlayList("Musica");
        this.lector = new LectorMP3();
        this.mapaAlbumes = new HashMap<>();
        this.treeViewBiblioteca = new TreeView<>();

        this.button_addFiles = new Button("Add Files");

        
        this.button_prev = new Button("<<");
        this.button_play = new Button("Play");
        this.button_pause = new Button("Pause");
        this.button_stop = new Button("Stop");
        this.button_next = new Button(">>");

        this.button_aleatorio = new Button("Aleatorio");

        // Botones para ordenar la playlist
        this.button_ordenarTitulo = new Button("Ordenar por Título");
        this.button_ordenarArtista = new Button("Ordenar por Artista");
        this.button_ordenarAnio = new Button("Ordenar por Año");
        
        // Label y ListView para mostrar la canción actual y la playlist
        this.labelCancionActual = new Label("Canción actual: ");
        this.listViewPlaylist = new ListView<>();
        this.listViewPlaylist.setPrefHeight(150);
        this.listViewPlaylist.setPrefWidth(400);

        registrarEventos();
    }

    private void registrarEventos() {
        button_addFiles.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos MP3", "*.mp3"));

            File archivo = fileChooser.showOpenDialog(null);

            if(archivo != null) {
                Cancion cancion = lector.leer(archivo);
                if (cancion != null) {
                    this.playlist.insertar(cancion, playlist.tamanio());
                    this.reproductor.cargarPlayList(this.playlist);
                    System.out.println("Cancion agregada: "+ cancion);
                }
                else{
                    System.out.println("No se puede leer el archivo.");
                }
                
            }
            //this.reproductor.cargarPlayList(this.playlist);
            actualizarListView();
        });

        this.button_prev.setOnAction(e -> {
            this.reproductor.anterior();
            actualizarLabelCanciones();
        });

        this.button_play.setOnAction(e -> {
            this.reproductor.play();
            actualizarLabelCanciones();
        });
        
        this.button_pause.setOnAction(e -> this.reproductor.pause());
        this.button_stop.setOnAction(e -> this.reproductor.stop());

        this.button_next.setOnAction(e -> {
            System.out.println("SIGUIENTE");
            this.reproductor.siguiente();
            actualizarLabelCanciones();
        });

        this.button_ordenarTitulo.setOnAction(e -> {
            this.playlist.ordenar(PlayListAbs.OrdenCriterio.TITULO);
            this.reproductor.cargarPlayList(this.playlist);
            actualizarListView();
        });
        this.button_ordenarArtista.setOnAction(e -> {
            this.playlist.ordenar(PlayListAbs.OrdenCriterio.ARTISTA);
            this.reproductor.cargarPlayList(this.playlist);
            actualizarListView();
        });
        this.button_ordenarAnio.setOnAction(e -> {
            this.playlist.ordenar(PlayListAbs.OrdenCriterio.ANIO);
            this.reproductor.cargarPlayList(this.playlist);
            actualizarListView();
        });
        this.button_aleatorio.setOnAction(e -> {
            this.reproductor.toggleAleatorio();
            actualizarAleatorio();
            actualizarListView();
        });

        this.listViewPlaylist.setOnMouseClicked(e -> {
            int indice = listViewPlaylist.getSelectionModel().getSelectedIndex();
            reproductor.seleccionarCancion(indice);
            reproductor.play();
            
            actualizarListView();
            actualizarLabelCanciones();
        });

    }

    private VBox crearLayout() {
        HBox addFilesBox = new HBox();
        addFilesBox.setSpacing(10);
        addFilesBox.getChildren().add(button_addFiles);
        addFilesBox.setAlignment(Pos.TOP_LEFT);
        
        HBox playlistBox = new HBox();
        playlistBox.setSpacing(10);
        playlistBox.setPrefHeight(200);
        playlistBox.getChildren().add(listViewPlaylist);
        playlistBox.setAlignment(Pos.CENTER);

        HBox controles = new HBox();
        controles.setSpacing(10);
        controles.getChildren().addAll(button_prev,button_play, button_pause, button_stop, button_aleatorio, button_next);
        controles.setAlignment(Pos.CENTER);

        HBox ordenar = new HBox();
        ordenar.setSpacing(10);
        ordenar.getChildren().addAll(button_ordenarTitulo, button_ordenarArtista, button_ordenarAnio);
        ordenar.setAlignment(Pos.CENTER);


        VBox root = new VBox();
        root.setSpacing(20);
        root.getChildren().addAll(addFilesBox, playlistBox, this.labelCancionActual, controles, ordenar);
        root.setAlignment(Pos.CENTER);

        return root;
    }

    private void actualizarListView() {
        this.listViewPlaylist.getItems().clear();

        for(int i = 0; i < this.playlist.tamanio(); i++) {
            Cancion cancion = (Cancion) this.playlist.devolver(i);
            this.listViewPlaylist.getItems().add(cancion.toString());
        }
    }

    private void actualizarAleatorio() {
        if(this.reproductor.esAleatorio()) {
            button_aleatorio.setText("🔀 ON");
        }
        else {
            button_aleatorio.setText("🔀 OFF");
        }
    }

    private void actualizarLabelCanciones() {
        Cancion cancion = (Cancion) this.reproductor.getCancionActual();
        if(cancion != null) {
            this.labelCancionActual.setText("🎶"+ cancion.toString());
        }
        else {
            this.labelCancionActual.setText("Sin reproducción actual");
        }
    }

    //Configura Drag and Drop   ---> Sirve para arrastrar archivos a la ventana y agregarlos a la playlist

    private void habilitarDragAndDrop() {
        //evento 1: Detectar cuando se arrastra un archivo sobre el ListView
        listViewPlaylist.setOnDragOver(event -> {
            if (event.getGestureSource() != listViewPlaylist && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        //evento 2: Detectar cuando se suelta el archivo sobre el ListView
        listViewPlaylist.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean complete = false; 

            if(db.hasFiles()) {
                complete = true;
                //Recorre todos los archivos soltados...
                for(File archivo: db.getFiles()) {
                    //Verifica que sean archivos mp3
                    if(archivo.getName().toLowerCase().endsWith(".mp3")) {
                        Cancion cancion = lector.leer(archivo);
                        if (cancion != null) { //Si se pudo leer la cancion, se agrega a la playlist
                            listViewPlaylist.getItems().add(cancion.toString());
                            this.playlist.agregarCancion(cancion);
                            clasificarCancionesPorAlbum(cancion);
                            System.out.println("Cancion agregada por Drag and Drop: "+ cancion);
                        }
                        else{
                            System.out.println("No se puede leer el archivo: " + archivo.getName());
                        }
                    }
                }
                this.reproductor.cargarPlayList(this.playlist);

                event.setDropCompleted(complete);
                event.consume();
            }
        });
    }

    private void clasificarCancionesPorAlbum(Cancion cancion) {
        if(cancion == null ){
            System.out.println("Error: La canción es nula.");
        }
        else {
            String album = cancion.getAlbum();
            if (album == null || album.isEmpty()) {
                album = "Desconocido";
            }

            if (!mapaAlbumes.containsKey(album)) {
                mapaAlbumes.put(album, new PlayList(cancion.getTitulo()));
            }
            mapaAlbumes.get(album).agregarCancion(cancion);
        }
        //Aqui se puede actualizar el TreeView para mostrar los albumes y las canciones dentro de cada album
        construirArbolAlbumes();
    }

    private void construirArbolAlbumes() {
        TreeItem<String> raiz = new TreeItem<>("Bibilioteca");
        raiz.setExpanded(true);

        for(String album: mapaAlbumes.keySet()) {
            TreeItem<String> nodoAlbum = new TreeItem<>(album);
            PlayList listaAlbum = mapaAlbumes.get(album);

            for (int i = 0; i < listaAlbum.tamanio(); i++) {
                Cancion cancion = (Cancion) listaAlbum.devolver(i);
                TreeItem<String> nodoCancion = new TreeItem<>(cancion.getTitulo());
                nodoAlbum.getChildren().add(nodoCancion);
            }

            raiz.getChildren().add(nodoAlbum);
        }
        treeViewBiblioteca.setRoot(raiz);
    }




}
