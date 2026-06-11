package Reproductor;

public class test {
    public static void main(String[] args) {
        //ReproductorMP3 reproductor = new ReproductorMP3();
        LectorMP3 lector = new LectorMP3();

        PlayList playlist = new PlayList("Mi Playlist");

        Cancion cancion1 = lector.leer(new java.io.File("C:\\Users\\sebas\\Downloads\\Harry Styles - Sign of the Times (Official Video).mp3"));
        Cancion cancion2 = lector.leer(new java.io.File("C:\\Users\\sebas\\Downloads\\Amárrame.mp3"));
        Cancion cancion3 = lector.leer(new java.io.File("C:\\Users\\sebas\\Downloads\\ss501  because i'm stupid sub español BOYS OVER FLOWERS.mp3"));

        playlist.agregarCancion(cancion1);
        playlist.agregarCancion(cancion2);
        playlist.agregarCancion(cancion3);

        playlist.mostrarPlaylist();

        playlist.ordenar(PlayListAbs.OrdenCriterio.TITULO);

        playlist.mostrarPlaylist();

        // reproductor.play();
        
    }
}
