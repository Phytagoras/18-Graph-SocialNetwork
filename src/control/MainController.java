package control;

import model.Edge;
import model.Graph;
import model.List;
import model.Vertex;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class MainController{

    //Attribute

    //Referenzen
    private final Graph allUsers;

    public MainController(){
        allUsers = new Graph();
        createSomeUsers();
    }

    /**
     * Fügt Personen dem sozialen Netzwerk hinzu.
     */
    private void createSomeUsers(){
        insertUser("Ulf");
        insertUser("Silent Bob");
        insertUser("Dörte");
        insertUser("Ralle");
        befriend("Silent Bob", "Ralle");
        befriend("Dörte", "Ralle");
    }

    /**
     * Fügt einen Nutzer hinzu, falls dieser noch nicht existiert.
     *
     * @param name
     * @return true, falls ein neuer Nutzer hinzugefügt wurde, sonst false.
     */
    public boolean insertUser(String name){
        //TODO 05: Nutzer dem sozialen Netzwerk hinzufügen.
        if(allUsers.getVertex(name) == null){
            allUsers.addVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Löscht einen Nutzer, falls dieser existiert. Alle Verbindungen zu anderen Nutzern werden ebenfalls gelöscht.
     *
     * @param name
     * @return true, falls ein Nutzer gelöscht wurde, sonst false.
     */
    public boolean deleteUser(String name){
        //TODO 07: Nutzer aus dem sozialen Netzwerk entfernen.
        Vertex vertex = allUsers.getVertex(name);
        if(vertex != null){
            allUsers.removeVertex(vertex);
            return true;
        }
        return false;
    }

    /**
     * Falls Nutzer vorhanden sind, so werden ihre Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     *
     * @return
     */
    public String[] getAllUsers(){
        //TODO 06: String-Array mit allen Nutzernamen erstellen.
        if(!allUsers.isEmpty()){
            List<Vertex> l = allUsers.getVertices();
            int x = 0;
            l.toFirst();
            while(l.hasAccess()){
                x++;
                l.next();
            }

            String[] s = new String[x];
            l.toFirst();
            for(int i = 0; i < x; i++){
                s[i] = l.getContent().getID();
                l.next();
            }
            return s;
        }
        return null;
    }

    /**
     * Falls der Nutzer vorhanden ist und Freunde hat, so werden deren Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     *
     * @param name
     * @return
     */
    public String[] getAllFriendsFromUser(String name){
        //TODO 09: Freundesliste eines Nutzers als String-Array erstellen.
        if(allUsers.getVertex(name) != null){
            Vertex user = allUsers.getVertex(name);
            List<Vertex> l = allUsers.getNeighbours(user);

            int a = 0;
            l.toFirst();
            while(l.hasAccess()){
                a++;
                l.next();
            }

            String[] s = new String[a];
            l.toFirst();
            for(int i = 0; i < a; i++, l.next()){
                s[i] = l.getContent().getID();
            }
            return s;
        }
        return null;
    }


    /**
     * Bestimmt den Zentralitätsgrad einer Person im sozialen Netzwerk, falls sie vorhanden ist. Sonst wird -1.0 zurückgegeben.
     * Der Zentralitätsgrad ist der Quotient aus der Anzahl der Freunde der Person und der um die Person selbst verminderten Anzahl an Nutzern im Netzwerk.
     * Gibt also den Prozentwert an Personen im sozialen Netzwerk an, mit der die Person befreundet ist.
     *
     * @param name
     * @return
     */
    public double centralityDegreeOfUser(String name){
        //TODO 10: Prozentsatz der vorhandenen Freundschaften eines Nutzers von allen möglichen Freundschaften des Nutzers.
        if(allUsers.getVertex(name) != null){
            double finity = getAllFriendsFromUser(name).length;
            double amountOfUsers = getAllUsers().length - 1;
            if(amountOfUsers != 0){
                return finity / amountOfUsers;
            }
            return 0;
        }
        return -1;
    }

    /**
     * Zwei Nutzer des Netzwerkes gehen eine Freundschaft neu ein, falls sie sich im Netzwerk befinden und noch keine Freunde sind.
     *
     * @param name01
     * @param name02
     * @return true, falls eine neue Freundeschaft entstanden ist, ansonsten false.
     */
    public boolean befriend(String name01, String name02){
        //TODO 08: Freundschaften schließen.
        if(allUsers.getEdge(allUsers.getVertex(name01), allUsers.getVertex(name02)) == null){
            allUsers.addEdge(new Edge(allUsers.getVertex(name01), allUsers.getVertex(name02), 1));
            return true;
        }
        return false;
    }


    /**
     * Zwei Nutzer beenden ihre Freundschaft, falls sie sich im Netzwerk befinden und sie befreundet sind.
     *
     * @param name01
     * @param name02
     * @return true, falls ihre Freundschaft beendet wurde, ansonsten false.
     */
    public boolean unfriend(String name01, String name02){
        //TODO 11: Freundschaften beenden.
        Edge tmp = allUsers.getEdge(allUsers.getVertex(name01), allUsers.getVertex(name02));
        if(tmp != null){
            allUsers.removeEdge(tmp);
            return true;
        }
        return false;
    }

    /**
     * Bestimmt die Dichte des sozialen Netzwerks und gibt diese zurück.
     * Die Dichte ist der Quotient aus der Anzahl aller vorhandenen Freundschaftsbeziehungen und der Anzahl der maximal möglichen Freundschaftsbeziehungen.
     *
     * @return
     */
    public double dense(){
        //TODO 12: Dichte berechnen.
        //Kleiner Gauss: (n^2 + n)/2
        List<Vertex> tmpVertices = allUsers.getVertices();
        List<Edge> tmpEdges = allUsers.getEdges();
        double trouble = -1;
        double kill = 0;
        tmpVertices.toFirst();
        while(tmpVertices.hasAccess()){
            trouble++;
            tmpVertices.next();
        }
        tmpEdges.toFirst();
        while(tmpEdges.hasAccess()){
            kill++;
            tmpEdges.next();
        }
        return (trouble>0)?kill/(((trouble*trouble)+trouble)/2):0;
    }

    /**
     * Gibt die möglichen Verbindungen zwischen zwei Personen im sozialen Netzwerk als String-Array zurück,
     * falls die Personen vorhanden sind und sie über eine oder mehrere Ecken miteinander verbunden sind.
     *
     * @param name01
     * @param name02
     * @return
     */
    public String[] getLinksBetween(String name01, String name02){
        Vertex user01 = allUsers.getVertex(name01);
        Vertex user02 = allUsers.getVertex(name02);
        if(user01 != null && user02 != null){
            //TODO 13: Schreibe einen Algorithmus, der mindestens eine Verbindung von einem Nutzer über Zwischennutzer zu einem anderem Nutzer bestimmt. Happy Kopfzerbrechen!
        }
        return null;
    }
}
