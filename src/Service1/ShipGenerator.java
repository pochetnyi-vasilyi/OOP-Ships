package Service1;

import java.sql.Timestamp;
import java.util.*;

public class ShipGenerator {

    private final List<Ship> ships;
    private static final int MAX_CARGO_WEIGHT = 300;
    private static final String[] cargoShipsNames = new String[] {"Al Rekayyat", "MV Algosteel", "SS Amasa Stone",
            "MV Algocape", "MV Algorail", "MV Algosoo", "MV Algolake", "Algoma Equinox", "Algoma Buffalo", "Algoma Compass",
            "Algoma Mariner", "Algoma Montrealais", "Algoma Navigator", "Algoma Quebecois", "Algoma Progress",
            "Algoma Sault", "SS Asiatic", "SS Afric", "SS Atlantic Causeway", "SS Atlantic Conveyor", "Axel Maersk",
            "Arnold Maersk", "MV Acavus", "MV American Century", "MV American Courage", "MV American Mariner",
            "MV American Integrity", "SS Argus", "MV Adula", "Akebono Maru", "MV Alexia", "Altmark", "MV Amastra",
            "Amoco Cadiz", "MV Ancylus", "USS Adhara", "USS Albireo", "USS Alderamin", "USS Alkaid", "USS Alkes",
            "USS Allegan", "USS Allioth", "USS Alnitah", "USS Aludra", "SS American Victory", "MS Antenor",
            "USS Appanoose", "USS Ara", "Aranui 3", "USS Arided", "USS Arkab", "SS Arthur M. Anderson", "USS Ascella",
            "MV Ascension", "Astron", "USS Azimech", "MV eut Strait", "SS Bandırma", "MV Capt. Steven L. Bennett",
            "SS Bessemer Victory", "Biruinţa", "MV Biscaglia", "USS Bali", "SS Belgic", "SS Bovic", "SS Bardic",
            "MV Baie Comeau", "MV Burns Harbor", "MV Baie St Paul", "SS Benjamin Noble", "CMA CGM Medea",
            "CMA CGM Thalassa", "CMA CGM Vela", "COSCO Guangzhou", "MV Calumet", "MV Canadian Miner", "MV Colombo Express",
            "MS Costa Allegra", "MS Costa Marina", "MV Craigantlet", "SS Charles S Price", "SS Carl D. Bradley",
            "MV Cristina A", "SS Cufic", "MV Delight", "SS Desabla", "SS Delphic", "SS Daniel J. Morrell", "Edith Maersk",
            "Emma Maersk", "Estelle Maersk", "MOL Enterprise", "MV Edwin H Got", "SS Edmund Fitzgerald", "MV Edward L Ryerson",
            "SS Emperor", "SS Eaglescliffe hall", "SS Fatima", "SS Francis Hinton", "SS Florida", "SS Frank C Ball",
            "SS Frank O'Connor", "LNG Finima", "MV Federal Oshima", "Federal Rideau", "MV Gadila", "MV Golden Nori",
            "SS Gallina", "TSS Golfito", "Gudrun Maersk", "SS Georgic", "SS Galeic", "SS Gallic", "Hansa Carrier",
            "MV H Lee White", "MV Harpa", "Houston Express", "Histria Agata", "Histria Azure", "Histria Coral",
            "Hudson Cavalier", "SS Henry B Smith", "SS Howard M. Hanna Jr", "SS Hydrus", "SS Henry A. Hawgood",
            "SS Ideal X", "SS Isaac M. Scott", "MV Indiana Harbor", "MV Jindal Kamakshi", "MV John J Boland", "SS J B Ford",
            "MV James R Barker", "SS James Carruthers", "SS John A. McGean", "SS James B Colgate", "MV Kaye E Baker",
            "SS Kamloops", "MV Karen Danielsen", "SS L.R. Doty", "SS Louisiana", "LT Cortesia", "Maharshi Krishnatreya",
            "Maharshi Vamadeva", "Maharshi Bhardwaj", "MCP Altona", "MSC Carmen", "MSC Carouge", "MSC Cordoba", "MSC Geneva",
            "MSC Leigh", "MSC Monterey", "MSC Napoli", "MSC Nuria", "MSC Pamela", "MSC Rosaria", "MSC Sabrina",
            "MV Avenue Star", "MV Mairangi Bay", "MV Maj. Bernard F. Fisher", "SS Mayaguez", "MV Monchegorsk",
            "MV Empire MacCabe", "MV Empire MacColl", "MV Empire MacKay", "MV Empire MacMahon", "MV Erika", "MT Haven",
            "MV DenDen", "MV Macoma", "Maritime Jewel", "SS Medic", "MV Mitchipicoten", "MV Mississagi", "MV Mesabi Miner",
            "NYK Vega", "SS Naronic", "SS Nomadic", "Olga Maersk", "Onaiza", "Osaka Express", "Onoko", "Ohio",
            "MV Paul R Tregutrhua", "SS Prospector", "Quinquereme", "MV Ranga", "MV Resolution Bay", "SS Runic",
            "MV Robert S Pierson", "SS Regina", "MV Radcliffe R Latimer", "MV Roger Blough", "RJ Hacket", "Savannah Express",
            "MV Southern Lily", "MV Star Osakana", "SS Fredericksburg", "Salem", "MV Sam Laud", "MV Sea Empress",
            "MV Sea Star", "Seabulk Pride", "USS Sherman", "Shuttle tanker", "MT Sidsel Knutsen", "MV Sirius Star",
            "Sitakund", "MS Star Clipper", "Mærsk Stralsund", "MV Stolt Strength", "MS Stolt Surf", "MT Stolt Valor",
            "SS Suevic", "SS St. Marys Challenger", "SS Saginaw", "MV Thunder bay", "MV Tecumseh", "MV Tim S Dool",
            "MV Tampa", "MT Trochus", "Thomas W. Lawson", "Torben Spirit", "Torrey Canyon", "Truelove", "SS Tropic",
            "SS Tauric", "SS Thomas F. Cole", "SS Thomas Wilson", "Umm Laqhab", "Umm Slal", "U-Sea Colonsay",
            "U-Sea Saskatchewan", "Exxon Valdez", "MV Vikartindur", "MV Virginian", "SS Vedic", "SS Valley Camp",
            "MV Whitefish Bay", "MV Walter J McCarthy", "SS William Clay Ford", "SS William C. Moreland",
            "SS William B. Davock", "SS Western Reserve", "SS W.H. Gilcher", "SS Willis L. King", "SS Willam G Mather",
            "SS Willam H Donner", "SS William A Irvan", "Xin Los Angeles"};
    private static final String[] typeNames = new String[] {"bulk", "liquid", "container"};

    public ShipGenerator (int number) {
        ships = new ArrayList<Ship>();
        for (int i = 0; i < number; i++) {
            try {
                ships.add(CreateShip());
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        ships.sort(Comparator.comparing(Ship::getEstimatedBeginTime));
        System.out.println(ships);
    }

    public List<Ship> getShips () {
        return ships;
    }

    @Override
    public String toString () {
        StringBuilder string = new StringBuilder("Schedule of ships\n");
        for (int i = 0; i < ships.size(); i++) {
            string.append("Ship №").append(i + 1).append("\n").append(ships.get(i)).append("\n\n");
        }
        return string.toString();
    }

    public Ship.Cargo CreateCargo () {
        String name = typeNames[(int) (Math.random() * typeNames.length)];
        int weight = (int) (Math.random() * MAX_CARGO_WEIGHT) + 1;
        return new Ship.Cargo(name, weight);
    }

    private Ship CreateShip () {
        int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
        int month = GregorianCalendar.getInstance().get(Calendar.MONTH) + 1;
        final long begin = Timestamp.valueOf("" + year + "-" + month + "-01 00:00:00").getTime();
        final long end = Timestamp.valueOf("" + year + "-" + month + "-30 23:59:59").getTime();
        Timestamp beginTime = new Timestamp(begin + (long) (Math.random() * (end - begin + 1)));
        Random random = new Random();
        return new Ship(cargoShipsNames[random.nextInt(cargoShipsNames.length)], beginTime, CreateCargo());
    }

}
