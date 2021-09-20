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
        insertUser("Erik");
        insertUser("Simon");
        insertUser("Leyla");
        insertUser("Max");
        insertUser("Robin");
        insertUser("Niklas1");
        insertUser("Niklas2");
        insertUser("Herr Ambrosius");
        insertUser("1");
        insertUser("2");
        befriend("Herr Ambrosius", "Erik");
        befriend("Herr Ambrosius", "1");
        befriend("Erik", "Leyla");
        befriend("Erik", "Simon");
        befriend("Simon", "Robin");
        befriend("Leyla", "Niklas1");
        befriend("Niklas1", "Max");
        befriend("Robin", "Max");
        befriend("2", "Max");
        befriend("Max", "Niklas2");
        befriend("Silent Bob", "Ralle");
        befriend("Dörte", "Ralle");
        System.out.println(breiteIterativ("Niklas1", "1"));
        /*insertUser("alex");
        insertUser("yoda");
        insertUser("pia");
        insertUser("alf");
        insertUser("chuck");
        insertUser("ute");
        insertUser("uwe");
        insertUser("jan");
        befriend("alex", "pia");
        befriend("pia", "yoda");
        befriend("pia", "jan");
        befriend("pia", "ute");
        befriend("ute", "jan");
        befriend("jan", "chuck");
        befriend("jan", "uwe");
        befriend("chuck", "alf");
        befriend("uwe", "alf");
        befriend("uwe", "chuck");
        String[] arr = getCluster("uwe");
        for(String str : arr){
            System.out.println(str + "\n");
        }

         /*
        insertUser("s");
        insertUser("a");
        insertUser("c");
        insertUser("z");
        insertUser("b");
        insertUser("d");
        befriend("s", "a");
        befriend("s", "b");
        befriend("a", "c");
        befriend("a", "b");
        befriend("a", "d");
        befriend("b", "d");
        befriend("c", "d");
        befriend("c", "z");
        befriend("d", "z");

         */
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
        return (trouble > 0) ? kill / (((trouble * trouble) + trouble) / 2) : 0;
    }

    /**
     * Gibt die möglichen Verbindungen zwischen zwei Personen im sozialen Netzwerk als String-Array zurück,
     * falls die Personen vorhanden sind und sie über eine oder mehrere Ecken miteinander verbunden sind.
     *
     * @param name01
     * @param name02
     * @return
     */
    public String[][] getLinksBetween(String name01, String name02){
        Vertex user01 = allUsers.getVertex(name01);
        Vertex user02 = allUsers.getVertex(name02);
        if(user01 != null && user02 != null){
            //TODO 13: Schreibe einen Algorithmus, der mindestens eine Verbindung von einem Nutzer über Zwischennutzer zu einem anderem Nutzer bestimmt. Happy Kopfzerbrechen!
            //
            //<start>
            //
            List<Vertex> temporaryPath = new List<>();
            List<List<Vertex>> allPaths = new List<>();
            allUsers.setAllVertexMarks(false);
            temporaryPath.append(user01);
            user01.setMark(true);
            searchForConnections(user01, user02, temporaryPath, allPaths);
            user01.setMark(false);
            //
            //--Nur noch die Ausgabe--
            if(allPaths.isEmpty()) return null;
            allPaths.toFirst();
            int length = 0;
            while(allPaths.hasAccess()){
                length++;
                allPaths.next();
            }
            allPaths.toFirst();
            String[][] allPathsArray = new String[length][];
            for(int i = 0; i < allPathsArray.length; i++){
                allPaths.getContent().toFirst();
                int len = 0;
                while(allPaths.getContent().hasAccess()){
                    len++;
                    allPaths.getContent().next();
                }
                allPathsArray[i] = new String[len];
                allPaths.getContent().toFirst();
                for(int j = 0; j < allPathsArray[i].length; j++){
                    allPathsArray[i][j] = allPaths.getContent().getContent().getID();
                    allPaths.getContent().next();
                }
                allPaths.next();
            }
            return allPathsArray;
        }
        return null;
    }

    private void searchForConnections(Vertex start, Vertex wantedUser, List<Vertex> temporaryPath, List<List<Vertex>> allPaths){
        if(start == wantedUser){
            List<Vertex> tmpPath = new List<>();
            temporaryPath.toFirst();
            while(temporaryPath.hasAccess()){
                tmpPath.append(temporaryPath.getContent());
                System.out.println(temporaryPath.getContent().getID());
                temporaryPath.next();
            }
            allPaths.append(tmpPath);
            return;
        }
        List<Vertex> neighbors = allUsers.getNeighbours(start);
        neighbors.toFirst();
        while(neighbors.hasAccess()){
            Vertex tmp = neighbors.getContent();
            if(!tmp.isMarked()){
                tmp.setMark(true);
                temporaryPath.append(tmp);
                searchForConnections(tmp, wantedUser, temporaryPath, allPaths);
                temporaryPath.toLast();
                temporaryPath.remove();
                tmp.setMark(false);
            }
            neighbors.next();
        }
    }


    /**
     * Gibt zurück, ob es sich bei allUsers um einen zusammenhängenden Graphen handelt, also kein Knoten ohne Nachbarn ist.
     *
     * @return true, falls zusammenhängend, sonst false.
     */
    public boolean testIfConnectedEasy(){
        //TODO 14: Schreibe einen Algorithmus, der explizit den von uns benutzten Aufbau der Datenstruktur Graph und ihre angebotenen Methoden so ausnutzt, dass schnell (!) iterativ geprüft werden kann, ob der Graph allUsers zusammenhängend ist. Dies lässt sich mit einer einzigen Schleife prüfen.
        return false;
    }

    /**
     * Gibt zurück, ob vom ersten Knoten in der Liste aller Knoten ausgehend alle anderen Knoten erreicht, also markiert werden können.
     * Nach der Prüfung werden noch vor der Rückgabe alle Knoten demarkiert.
     *
     * @return true, falls alle Knoten vom ersten ausgehend markiert wurden, sonst false.
     */
    public boolean testIfConnectedTough(){
        //TODO 15: Schreibe einen Algorithmus, der ausgehend vom ersten Knoten in der Liste aller Knoten versucht, alle anderen Knoten über Kanten zu erreichen und zu markieren.
        List<Vertex> users = allUsers.getVertices();
        users.toFirst();
        testToughRec(users.getContent());
        if(allUsers.allVerticesMarked()){
            allUsers.setAllVertexMarks(false);
            return true;
        } else{
            allUsers.setAllVertexMarks(false);
            return false;
        }
    }

    private void testToughRec(Vertex v){
        if(!v.isMarked()){
            v.setMark(true);
            List<Vertex> neighbors = allUsers.getNeighbours(v);
            neighbors.toFirst();
            while(neighbors.hasAccess()){
                testToughRec(neighbors.getContent());
                neighbors.next();
            }
        }
    }

    public String[] getCluster(String startPoint){
        List<Vertex> clusterVert = new List<>();
        if(allUsers.getVertex(startPoint) != null){
            allUsers.setAllVertexMarks(false);
            getClusterPartRec(allUsers.getVertex(startPoint));
            List<Vertex> allVertices = allUsers.getVertices();
            allVertices.toFirst();
            int count = 0;
            while(allVertices.hasAccess()){
                if(allVertices.getContent().isMarked()){
                    clusterVert.append(allVertices.getContent());
                    count++;
                }
                allVertices.next();
            }
            clusterVert.toFirst();
            String[] arrOut = new String[count];
            for(int i = 0; i < count; i++){
                arrOut[i] = clusterVert.getContent().getID();
                clusterVert.next();
            }
            return arrOut;

        }
        return null;
    }

    private void getClusterPartRec(Vertex startPoint){
        startPoint.setMark(true);
        List<Vertex> neighboursTmp = allUsers.getNeighbours(startPoint);
        neighboursTmp.toFirst();
        while(neighboursTmp.hasAccess()){
            if(!neighboursTmp.getContent().isMarked()){
                getClusterPartRec(neighboursTmp.getContent());
            }
            neighboursTmp.next();
        }
    }
    public String breiteIterativ(String name, String endName){
        Vertex start = allUsers.getVertex(name);
        Vertex end = allUsers.getVertex(endName);
        if(start != null && end != null){
            allUsers.setAllEdgeMarks(false);
            List<Vertex> help = new List<>();
            Vertex[][] arrWay =  new Vertex[getAllUsers().length][2];
            help.append(start);
            int countPlace = 0;
            arrWay[countPlace][0] = start;
            arrWay[countPlace][1] = start;
            countPlace++;
            start.setMark(true);
            help.toFirst();
            while(help.hasAccess()){
                List<Vertex> l = allUsers.getNeighbours(help.getContent());
                l.toFirst();
                while(l.hasAccess()){
                    if(l.getContent().isMarked() == false){
                        help.append(l.getContent());
                        arrWay[countPlace][0] = l.getContent();
                        arrWay[countPlace][1] = help.getContent();
                        countPlace++;
                        l.getContent().setMark(true);
                    }
                    l.next();
                }
                help.next();
            }
            String out = "";
            Vertex ac = end;
            while(ac != start){
                out += ac.getID();
                for(int i = 0; i < arrWay.length; i ++){
                    if(arrWay[i][0] == ac){
                        ac = arrWay[i][1];
                    }
                }
            }
            return out;
        }
        return "nulllll";
    }

}
