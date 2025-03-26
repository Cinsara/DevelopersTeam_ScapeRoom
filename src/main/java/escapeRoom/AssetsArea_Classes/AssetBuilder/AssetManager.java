package escapeRoom.AssetsArea_Classes.AssetBuilder;

import escapeRoom.AssetsArea_Classes.CertificateBuilder.Certificate;
import escapeRoom.AssetsArea_Classes.RewardBuilder.Reward;
import escapeRoom.AssetsArea_Classes.TicketBuilder.Ticket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AssetManager {

    public String generateTxt(Object typeDocument, LocalDate date, int user_id, int game_id) {
        String fileName, content, documentType;

        if(typeDocument == null){
            return "Error. The document can not be null.";
        }

        if(typeDocument instanceof Reward){
            documentType = "reward";
            fileName = "Reward - " + user_id + " - " + game_id + ".txt";
            content = "Reward:\nUser: " + user_id + "\nGame: " + game_id +
                    "\nDate: " + date.format(DateTimeFormatter.ISO_DATE);
        } else if(typeDocument instanceof Certificate){
            documentType = "certificate";
            fileName = "Certificate - " + user_id + " - " + game_id + ".txt";
            content = "Certificate:\nUser: " + user_id + "\nGame: " + game_id +
                    "\nDate: " + date.format(DateTimeFormatter.ISO_DATE);
        } else if(typeDocument instanceof Ticket){
            documentType = "ticket";
            fileName = "Ticket - " + user_id + " - " + game_id + ".txt";
            content = "Ticket:\nUser: " + user_id + "\nGame: " + game_id +
                        "\nDate: " + date.format(DateTimeFormatter.ISO_DATE);
        } else {
            return "Error: Unsupported document type.";
        }

        try{
            Path directory= Path.of("C:/mis_documentos"); //Se define la ruta donde guardar los archivos
            if(!Files.exists(directory)){ //Crea el directorio si no existe
                Files.createDirectories(directory);
            }
            //Combina la ruta del directorio con el nombre del archivo y retorna un nuevo objeto Path con la ruta completa
            Path filePath = directory.resolve(fileName);
            if(Files.exists(filePath)){
                throw new IOException("File already exists: " + filePath);
            }

            Files.writeString(filePath,content);
            return "Reward successfully created at: " +
                    filePath.toAbsolutePath();
        } catch (IOException e) {
            return "Error generating file: " + e.getMessage();
        }

    }
}
